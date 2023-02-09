import pickle
import argparse


def main(gen_file_path, pkl_file_path, output_file):
    with open(gen_file_path) as f1, open(pkl_file_path, "rb") as f2:
        srl_questions = f1.readlines()
        pickle_file = pickle.load(f2)

    natural_questions = []
    index = 0
    for st in pickle_file:
        for way in st.ways:
            generated = srl_questions[index]
            for key, value in way.label.items():
                if generated.__contains__('$' + key):
                    generated = generated.replace('$' + key, value)

            index += 1
            natural_questions.append(generated)

    with open(output_file, 'w') as f1:
        f1.write(''.join(natural_questions))


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("--gen", type=str, help="Path to generated questions file from Seq2Seq model")
    parser.add_argument("--pkl", type=str, help="Path to pickle file from test set")
    parser.add_argument("--out", type=str, help="File name to save natural language questions")
    args = parser.parse_args()

    main(args.gen, args.pkl, args.out)
