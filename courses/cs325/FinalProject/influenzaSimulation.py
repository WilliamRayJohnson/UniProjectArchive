'''
A simulation of Influenza B's evolution
William Ray Johnson
Last Edited: 4 December 2017
'''

from readfasta import *

def kimura2Parameter(sequence, a, b):
    mutationMatrix = {'A':[1-a-(2*b), b, a, a],
                      'C':[b, 1-a-(2*b), b, a],
                      'G':[a, b, 1-a-(2*b), b],
                      'T':[a, a, b, 1-a-(2*b)]}
    matNucleoOrder = ['A', 'C', 'G', 'T']

    for i in range(len(sequence)):
        noMutation = True
        probsCount = 0
        randomNum = 0
        while noMutation and probsCount < 3:
            randomNum = random.random()
            if randomNum <= mutationMatrix[sequence[i]][probsCount]:
                noMutation = False
            probsCount += 1

        if not(noMutation):
            sequence = sequence[:i] + matNucleoOrder[probsCount-1]+ sequence[i+1:]
            
    return sequence
