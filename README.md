Code for the EACL 2023 paper: Question Generation Using Sequence-to-Sequence Model with Semantic Role Labels

The system requirements are:
- Linux OS
- Python 3
- Java JRE 8

To run the code, first install the required python packages.

```
pip install -r requirements.txt
```

## Sequence-to-Sequence Model with Semantic Role Labels

### Semantic Role Labeler

Semantic Role Labeler is used to convert source sentences in [src-file.txt] into their semantic representations. Note that we will use [srl-file.txt] in the next steps.

```
python SRLer.py --input [src-file.txt] \
                --output [srl-file.txt]
```

### Question2SRL Mapper

The file [tgt-file.txt] contains target questions.

#### Hard-Question2SRL Mapper

```
python Question2SRL.py --type hard \
                       --src [src-file.txt] \
                       --tgt [tgt-file.txt] \
                       --srl [srl-file.txt] \
                       --pkl [pickle-file.pkl]
```

#### Soft-Question2SRL Mapper

```
python Question2SRL.py --type soft \
                       --src [src-file.txt] \
                       --tgt [tgt-file.txt] \
                       --srl [srl-file.txt] \
                       --pkl [pickle-file.pkl] \
                       --alpha 0.85
```

### Sequence-to-Sequence Learning

For fine-tuning BART, T5 and ProphetNet, we used the code from [Question Generation using transformers](https://github.com/patil-suraj/question_generation).


### SRL2Question Mapper

SRL2Question mapper transforms all SRLs in [generated-questions-file.txt] into words or phrases using [pickle-file.pkl].

```
python SRL2Question.py  --gen [generated-questions-file.txt] \
                        --pkl [pickle-file.pkl] \
                        --out [output-file.txt]
```

## Rule-based method

First, you need to download ClearNLP from [here](https://drive.google.com/file/d/1ss4M-UDBoIJIfqXyxwKwNeg612EbGShM/view?usp=share_link) to this directory. To run it, the following command can be used in the Linux terminal or Windows command prompt:

```
java -Xmx8192m -jar cnlpinterface.jar [input-file.txt] [output-srl.txt]
```

The file [output-srl.txt] produced above will be the input to RuleBasedGenerator.java.

## Evaluation

To calculate the metrics, we used [nlg-eval](https://github.com/Maluuba/nlg-eval) package.
