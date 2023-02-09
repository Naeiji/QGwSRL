import argparse
import os
import pickle
import re
from functools import partial

from sentence_transformers import SentenceTransformer, util

from utils import *

model = SentenceTransformer('all-MiniLM-L6-v2')


def helper(dic, match):
    word = match.group(0)
    return dic.get(word, word)


def replace(sentence, key, value, alpha):
    word_list = sentence.split(sep=" ")
    sentences = []
    for i in range(len(word_list)):
        for j in range(i + 1, len(word_list) + 1):
            sentences.append(' '.join(word_list[i:j]).strip())

    t_embedding = model.encode(value, convert_to_tensor=True)
    embeddings = model.encode(sentences, convert_to_tensor=True)

    cosine_scores = util.pytorch_cos_sim(embeddings, t_embedding)

    pairs = []
    for i in range(len(cosine_scores) - 1):
        pairs.append({'index': i, 'score': cosine_scores[i]})

    pairs = sorted(pairs, key=lambda x: x['score'], reverse=True)

    index = pairs[0]['index']
    score = pairs[0]['score'].item()

    if sentences[index] == sentence:
        index = pairs[1]['index']
        score = pairs[1]['score'].item()

    flag = score > alpha and not (key in ['$V', '$ARGM-MOD'] and index == 0)

    word_re = re.compile(r'\b[a-zA-Z]+\b')

    if flag:
        if len(sentences[index].split()) > 1:
            sentence = sentence.replace(sentences[index], key)
        else:
            sentence = word_re.sub(partial(helper, {sentences[index]: key}), sentence)

    return flag, sentence


def soft2srl(src_file_path, tgt_file_path, srl_file_path, pkl_file, alpha):
    with open(src_file_path) as f1, open(tgt_file_path) as f2, open(srl_file_path) as f3:
        stf = f1.readlines()
        qsf = f2.readlines()
        srl = f3.readlines()

    data = []
    index = 1
    for st, qs in zip(stf, qsf):
        st, qs = st.strip(), qs.strip()
        statement = Statement(st, qs)

        for i in range(index, len(srl)):
            s = srl[index]
            index += 1
            if s.startswith('\t'):
                labels = dict()
                replaced_st = s.strip()
                replaced_qs = qs
                brackets = re.findall('\[(.*?)\]', s)
                for bracket in brackets:
                    if not bracket.__contains__(': ') or bracket.__contains__(' : '):
                        continue

                    key, value = bracket.split(": ", 1)
                    labels[key] = value

                    flag, replaced_qs = replace(replaced_qs, '$' + key, value, alpha)
                    if not flag and key in ['V', 'ARGM-MOD']:
                        replaced_st = replaced_st.replace('[{}]'.format(bracket), value)
                    else:
                        replaced_st = replaced_st.replace('[{}]'.format(bracket), '$' + key)

                statement.ways.append(Label(labels, replaced_st, replaced_qs))
            else:
                break

        data.append(statement)

        write(data, pkl_file)


def hard2srl(src_file_path, tgt_file_path, srl_file_path, pkl_file):
    with open(src_file_path) as f1, open(tgt_file_path) as f2, open(srl_file_path) as f3:
        stf = f1.readlines()
        qsf = f2.readlines()
        srl = f3.readlines()

    data = []
    index = 1
    for st, qs in zip(stf, qsf):
        st, qs = st.strip(), qs.strip()
        statement = Statement(st, qs)

        for i in range(index, len(srl)):
            s = srl[index]
            index += 1
            if s.startswith('\t'):
                labels = dict()
                replaced_st = s.strip()
                replaced_qs = qs
                brackets = re.findall('\[(.*?)\]', s)
                for bracket in brackets:
                    if not bracket.__contains__(': ') or bracket.__contains__(' : '):
                        continue

                    key, value = bracket.split(": ", 1)
                    labels[key] = value

                    word_re = re.compile(r'\b[a-zA-Z]+\b')

                    if key == 'V':
                        replaced_st = replaced_st.replace('[{}]'.format(bracket), value)
                    else:
                        replaced_st = replaced_st.replace('[{}]'.format(bracket), '$' + key)

                        if len(value.split()) > 1:
                            replaced_qs = replaced_qs.replace(value, '$' + key)
                        else:
                            replaced_qs = word_re.sub(partial(helper, {value: '$' + key}), replaced_qs)

                statement.ways.append(Label(labels, replaced_st, replaced_qs))
            else:
                break

        data.append(statement)

        write(data, pkl_file)


def write(data, pkl_file):
    if not os.path.exists('out'):
        os.makedirs('out')

    with open('out/src-srl.txt', 'w') as src_file, open('out/tgt-srl.txt', 'w') as tgt_file:
        for statement in data:
            for label in statement.ways:
                src_file.write("%s\n" % label.statement)
                tgt_file.write("%s\n" % label.question)

    with open(f'out/{pkl_file}', 'wb') as f:
        pickle.dump(data, f)


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("--type", type=str, help="One of 'hard', 'soft'")
    parser.add_argument("--src", type=str, help="Path to source sentences file")
    parser.add_argument("--tgt", type=str, help="Path to target questions file")
    parser.add_argument("--srl", type=str, help="Path to srl file")
    parser.add_argument("--pkl", type=str, default="middle.pkl", help="Name of pickle file")
    parser.add_argument("--alpha", type=float, default="0.85", help="Alpha value")
    args = parser.parse_args()

    if args.type == 'soft':
        soft2srl(args.src, args.tgt, args.srl, args.pkl, args.alpha)
    elif args.type == 'hard':
        hard2srl(args.src, args.tgt, args.srl, args.pkl)
