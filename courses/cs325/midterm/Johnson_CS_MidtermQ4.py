'''
CS 325 Intro to Bioinformatics Fall 2016 Midterm: CS Version
Question #4: Muti-string alignment
William Ray Johnson
Last Edited: 23 October 2016
'''

import sys
from readfasta import *
from itertools import combinations

'''
Finds the optimal alignment by calculating a memo using dynamic programming.
row: the row cordinate of the solution
col: the col cordinate of the solution
DNA1: the 1st DNA sequence, makes up the rows
DNA2: the 2nd DNA sequence, makes up the cols
memo: the memo for keeping track of solutions
params: the various penalties and bonuses
    [match bonus, mismatch bonus, gap penalty]
return: the alignment score
'''
def calcMemo(row, col, DNA1, DNA2, memo, params):

    #Base Case
    if memo[row][col] != sys.maxsize:
        return memo[row][col]

    if DNA1[row - 1] == DNA2[col - 1]:
        memo[row][col] = max(calcMemo(row-1, col, DNA1, DNA2, memo, params) + params[2],
                             calcMemo(row, col-1, DNA1, DNA2, memo, params) + params[2],
                             calcMemo(row-1, col-1, DNA1, DNA2, memo, params) + params[0])

    else:
        memo[row][col] = max(calcMemo(row-1, col, DNA1, DNA2, memo, params) + params[2],
                             calcMemo(row, col-1, DNA1, DNA2, memo, params) + params[2],
                             calcMemo(row-1, col-1, DNA1, DNA2, memo, params) + params[1])

    return memo[row][col]
'''
Finds the alignment of two DNA sequences based on a memo
memo: the 2d array that contains the alignment scores between the two strands
DNA1: the 1st DNA sequence, makes up the rows
DNA2: the 2nd DNA sequence, makes up the cols
params: the various penalties and bonuses
    [match bonus, mismatch bonus, gap penalty]
return: a list containing the optimal alignment between the two inputed strands
'''
def backtrace(memo, DNA1, DNA2, params):
    row = len(DNA1)
    col = len(DNA2)
    alignedDNA1 = ''
    alignedDNA2 = ''

    while( (col != 0) or (row != 0)):
        if (memo[row-1][col] + params[2]) == memo[row][col]:
            alignedDNA1 += DNA1[row - 1]
            alignedDNA2 += '-'
            row -= 1
        elif (memo[row][col-1] + params[2]) == memo[row][col]:
            alignedDNA1 += '-'
            alignedDNA2 += DNA2[col - 1]
            col -= 1
        elif DNA1[row - 1] == DNA2[col - 1]:
            alignedDNA1 += DNA1[row - 1]
            alignedDNA2 += DNA2[col - 1]
            row -= 1
            col -= 1
        else:
            alignedDNA1 += DNA1[row - 1]
            alignedDNA2 += DNA2[col - 1]
            row -= 1
            col -= 1

    return [alignedDNA1[::-1],alignedDNA2[::-1]]

'''
Finds the alignment of two DNA sequences based on a memo and combineds them by
only adding matches to the alignment strand. gaps and mismatches are added on
with -
memo: the 2d array that contains the alignment scores between the two strands
DNA1: the 1st DNA sequence, makes up the rows
DNA2: the 2nd DNA sequence, makes up the cols
params: the various penalties and bonuses
    [match bonus, mismatch bonus, gap penalty]
return: The combined strand of only matches
'''
def backtraceMatchOnly(memo, DNA1, DNA2, params):
    row = len(DNA1)
    col = len(DNA2)
    alignedDNA = ''

    while( (col != 0) or (row != 0)):
        if (memo[row-1][col] + params[2]) == memo[row][col]:
            alignedDNA += '-'
            row -= 1
        elif (memo[row][col-1] + params[2]) == memo[row][col]:
            alignedDNA += '-'
            col -= 1
        elif DNA1[row - 1] == DNA2[col - 1]:
            alignedDNA += DNA1[row - 1]
            row -= 1
            col -= 1
        else:
            alignedDNA += '-'
            row -= 1
            col -= 1

    return alignedDNA[::-1]

'''
Constructs and blank memo and fills the the first row and col
row: number of rows
col: number of cols
params: the various penalties and bonuses
    [match bonus, mismatch bonus, gap penalty]
return: the constructed memo
'''
def buildBlankMemo(row, col, params):
    memo = []

    for i in range(row+1):
        memo.append([])
        for j in range(col+1):
            memo[i].append(sys.maxsize)

    memo[0][0] = 0
    for i in range(1,row+1):
        memo[i][0] = memo[i-1][0] + params[2]
    for j in range(1,col+1):
        memo[0][j] = memo[0][j-1] + params[2]

    return memo

'''
Finds the two DNA sequences with the best alignment score
DNAList: List of DNA to be combined
params: the various penalties and bonuses
    [match bonus, mismatch bonus, gap penalty]
return: the two strand with the best alignment and their memo
'''
def bestCombination(DNAList, params):
    maxval = -sys.maxsize
    bestcomb = []
    for DNAs in combinations(DNAList, 2):
        memo = buildBlankMemo(len(DNAs[0][2]), len(DNAs[1][2]), params)
        val = calcMemo(len(DNAs[0][2]), len(DNAs[1][2]),
                   DNAs[0][2], DNAs[1][2], memo, params)
        if val > maxval:
            maxval = val
            bestcomb = [DNAs[0], DNAs[1], memo]

    return bestcomb
    
     
def main(DNAFileName):
    sys.setrecursionlimit(100000)
    params = [5,-4,-11]
    DNAList = readfasta(DNAFileName)
    TempDNAList = DNAList[:]

    for i in range(len(DNAList) - 1):
        currentBest = bestCombination(TempDNAList, params)
        TempDNAList.remove(currentBest[0])
        TempDNAList.remove(currentBest[1])
        TempDNAList.append([str(i),str(i),
                            backtraceMatchOnly(
                                currentBest[2], currentBest[0][2],
                                currentBest[1][2], params)])
                
    bestAlignment = TempDNAList[0][2]

    for DNA in DNAList:
        memo = buildBlankMemo(len(DNA[2]), len(bestAlignment), params)
        val = calcMemo(len(DNA[2]), len(bestAlignment), DNA[2], bestAlignment,
                     memo, params)
        print('Alignment Score for ' + DNA[0] + ': ' + str(val))
        print(str(backtrace(memo, DNA[2], bestAlignment, params)[0]) + '\n')
    
main('MidCS4.txt')
