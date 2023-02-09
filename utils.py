class Statement:
    def __init__(self, statement, question):
        self.statement = statement
        self.question = question
        self.ways = []

    def __str__(self):
        return "{}\n{}\n{}".format(self.statement, self.question, '\n'.join(self.ways))


class Label:
    def __init__(self, label, statement, question):
        self.label = label
        self.statement = statement
        self.question = question
