'''
CS 325 Final Exam
Question 4
William Ray Johnson
Last Edited: 12/15/16
'''

from readfasta import *
from collections import Counter
import random

def getParsimonyScores(siteValues):
    alphaTree = siteValues
    betaTree = [siteValues[0], siteValues[2], siteValues[1], siteValues[3]]
    gammaTree = [siteValues[0], siteValues[3], siteValues[1], siteValues[2]]
    trees = [alphaTree, betaTree, gammaTree]
    parsimonyScores = [0, 0, 0]
    treeCount = 0
    
    for tree in trees:
        if tree[0] != tree[1]:
            parsimonyScores[treeCount] += 1
        if tree[2] != tree[3]:
            parsimonyScores[treeCount] += 1
        if not(tree[0] in tree[2:]) and not(tree[1] in tree[2:]):
            parsimonyScores[treeCount] += 1
        treeCount += 1

    return parsimonyScores
    
def isInformative(siteValues):
    siteValueCount = Counter(siteValues)
    maxValue = list(siteValueCount.values())[0]

    return len(siteValueCount) == 2 and maxValue == 2

def makeBootstrapReplicatesPrintable(replicates):
    repSeqs = [''] * len(replicates[0])
    repCount = 0

    for site in replicates:
        for nuc in site:
            repSeqs[repCount] += nuc
            repCount += 1
        repCount = 0
            
    return repSeqs         
            
def printConsensusTree(treeType, sequences):
    treeIndices = {'Alpha' : [0, 1, 2, 3],
                   'Beta' : [0, 2, 1, 3],
                   'Gamma' : [0, 3, 1, 2]}
    treeOrder = []
    spiciesLength = 16

    for seqIndex in treeIndices[treeType]:
        treeOrder.append(sequences[seqIndex][0])

    treeOrder[0] = format(treeOrder[0], ' <{}'.format(spiciesLength))
    treeOrder[1] = format(treeOrder[1], ' <{}'.format(spiciesLength))
    treeOrder[2] = format(treeOrder[2], ' >{}'.format(spiciesLength))
    treeOrder[3] = format(treeOrder[3], ' >{}'.format(spiciesLength))

    print('{}--       --{}'.format(treeOrder[0],treeOrder[2]))
    print(format('|     |', ' >{}'.format(spiciesLength+9)))
    print(format('|-----|', ' >{}'.format(spiciesLength+9)))
    print(format('|     |', ' >{}'.format(spiciesLength+9)))
    print('{}--       --{}'.format(treeOrder[1],treeOrder[3]))
    return
        
    
def main():
    sequences = readfasta('FinalProblemCS4.txt')
    consensusTrees = []
    resampleMatrices = []
    bootstrapReplicates = int(input('How many bootstrap replicates should be made?: '))

    for rep in range(bootstrapReplicates):
        treeSubsitutions = {'Alpha' : 0,
                            'Beta' : 0,
                            'Gamma' : 0}
        resampleMatrix = []
        for site in range(len(sequences[0][2])):
            siteValues = []
            randomSite = random.randrange(0, len(sequences[0][2]))
            for seq in sequences:
                siteValues.append(seq[2][randomSite])
            resampleMatrix.append(siteValues)

            if isInformative(siteValues):
                currentScores = getParsimonyScores(siteValues)
                treeSubsitutions['Alpha'] += currentScores[0]
                treeSubsitutions['Beta'] += currentScores[1]
                treeSubsitutions['Gamma'] += currentScores[2]
        
        currentConsensusTree = max(treeSubsitutions, key=treeSubsitutions.get)
        consensusTrees.append(currentConsensusTree)
        resampleMatrices.append(resampleMatrix)

    consensusTreeCount = Counter(consensusTrees)
    consensusTree = max(consensusTreeCount, key=consensusTreeCount.get)

    #Output
    for sample in range(3):
        replicates = makeBootstrapReplicatesPrintable(resampleMatrices[sample])
        print('Bootstrap number {}'.format(sample))
        for rep in range(len(replicates)):
            print('Replicate number {}'.format(rep))
            print(replicates[rep] + '\n')
        print('Supported Topology: ')
        printConsensusTree(consensusTrees[sample], sequences)
        print('\n')
        
    print('The Final Consensus Tree Topology:')
    printConsensusTree(consensusTree, sequences)
    print('Bootstrap Value: {}%'.format(
        consensusTreeCount[consensusTree]/bootstrapReplicates * 100))
    

if __name__ == '__main__':
    main()

            
            
