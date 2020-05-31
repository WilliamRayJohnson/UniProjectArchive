'''
CS 325 Intro to Bioinformatics Fall 2016 Midterm: CS Version
Question #1: Simulating molecular evolution
William Ray Johnson
Last Edited: 23 October 2016
'''

import random

def generateRandomDNASeq(length):
    nucleotides = ['A', 'G', 'C', 'T']
    sequence = ''
    for i in range(length):
        sequence += random.choice(nucleotides)

    return sequence

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

def naturalSelection(population, N, fitSeq):
    for i in population:
        if fitSeq in i:
           population 
    

def evolutionSim(N, L, G):
    originalSeq = generateRandomDNASeq(L)
    population = []
    for i in range(N):
        population.append(originalSeq)

    for i in range(G):
        for j in population:
            newJ = kimura2Parameter(j, .3, .1)
            population.remove(j)
            population.append(newJ)


    print('Original Sequence: ' + originalSeq)
    for i in population:
        print(i)

evolutionSim(10, 10, 400)
