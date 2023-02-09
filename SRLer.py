import argparse
from allennlp_models.pretrained import load_predictor


def main(input_path, output_path):
    predictor = load_predictor("structured-prediction-srl-bert")

    with open(input_path, 'r') as file:
        Lines = file.readlines()

    L = list()
    count = 0
    for line in Lines:
        count += 1
        L.append("{}\t{}".format(count, line.strip()))

        preds = predictor.predict(line.strip())
        for verb in preds["verbs"]:
            L.append("\t{}".format(verb['description']))

        print(count)

    L = map(lambda x: x + '\n', L)

    with open(output_path, 'w') as out:
        out.writelines(L)


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("--input", type=str, default="Path to input file")
    parser.add_argument("--output", type=str, default="Path to output file")
    args = parser.parse_args()

    main(args.input, args.output)
