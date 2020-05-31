'''
CS 325 Intro to Bioinformatics
Assignment 2
William Ray Johnson
Sankeerth Reddy
Kelsey Wilson
Last Edited: 14 November 2016
runtime around 20 minutes
'''

import sys
from readfasta import *
from itertools import combinations
from random import randint
from statistics import mean
	
'''
Finds the optimal local alignment by calculating a memo using dynamic programming.
row: the row cordinate of the solution
col: the col cordinate of the solution
DNA1: the 1st DNA sequence, makes up the rows
DNA2: the 2nd DNA sequence, makes up the cols
memo: the memo for keeping track of solutions
matMisScoring: A two dimentional dictionary that give the appropriate score for 
    any pairing of nucliotides
gapPenalty: the penalty for inserting a gap
return: the alignment score
'''
def calcLocalMemo(row, col, DNA1, DNA2, memo, matMisScoring, gapPenalty):

    #Base Case
    if memo[row][col] != sys.maxsize:
        return memo[row][col]

    score = max(calcLocalMemo(row-1, col, DNA1, DNA2, memo, matMisScoring, gapPenalty) + gapPenalty,
                calcLocalMemo(row, col-1, DNA1, DNA2, memo, matMisScoring, gapPenalty) + gapPenalty,
                calcLocalMemo(row-1, col-1, DNA1, DNA2, memo, matMisScoring, gapPenalty) 
                                + matMisScoring[DNA1[row-1]][DNA2[col-1]])
    if score < 0:
        memo[row][col] = 0
    else:
        memo[row][col] = score


    return memo[row][col]

'''
Finds the optimal semiglobal alignment by calculating a memo using dynamic
programming.
row: the row cordinate of the solution
col: the col cordinate of the solution
DNA1: the 1st DNA sequence, makes up the rows
DNA2: the 2nd DNA sequence, makes up the cols
memo: the memo for keeping track of solutions
matMisScoring: A two dimentional dictionary that give the appropriate score for 
    any pairing of nucliotides
    gapPenalty: the penalty for inserting a gap
    return: the alignment score
'''
def calcSemiGlobalMemo(row, col, DNA1, DNA2, memo, matMisScoring, gapPenalty):

    #Base Case
    if memo[row][col] != sys.maxsize:
        return memo[row][col]

    memo[row][col] = max(calcSemiGlobalMemo(row-1, col, DNA1, DNA2, memo, matMisScoring, 
                            gapPenalty) + gapPenalty,
                            calcSemiGlobalMemo(row, col-1, DNA1, DNA2, memo, matMisScoring, 
                                gapPenalty) + gapPenalty, 
                            calcSemiGlobalMemo(row-1, col-1, DNA1, DNA2, memo, matMisScoring, 
                                gapPenalty) + matMisScoring[DNA1[row-1]][DNA2[col-1]])                       
    return memo[row][col]

'''
Finds the alignment of two DNA sequences based on a memo used in local alignment
memo: the 2d array that contains the alignment scores between the two strands
DNA1: the 1st DNA sequence, makes up the rows
DNA2: the 2nd DNA sequence, makes up the cols
gapPenalty: the gap penalty used in the memo
startIndex: the location of the highest score in the memo
return: a list containing the optimal alignment between the two inputed strands
'''
def localBacktrace(memo, DNA1, DNA2, gapPenalty, startIndex):
    row = startIndex[0]
    col = startIndex[1]
    alignedDNA1 = ''
    alignedDNA2 = ''

    while( memo[row][col] != 0 ):
        if (memo[row-1][col] + gapPenalty) == memo[row][col]:
            alignedDNA1 += DNA1[row - 1]
            alignedDNA2 += '-'
            row -= 1
        elif (memo[row][col-1] + gapPenalty) == memo[row][col]:
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
Constructs and blank memo and fills the the first row and col
with 0 for use in a local alignment
row: number of rows
col: number of cols
return: the constructed memo
'''
def buildBlankLocalMemo(row, col):
    memo = []

    for i in range(row+1):
        memo.append([])
        for j in range(col+1):
            memo[i].append(sys.maxsize)

    memo[0][0] = 0
    for i in range(1,row+1):
        memo[i][0] = 0
    for j in range(1,col+1):
        memo[0][j] = 0

    return memo

'''
Constructs and blank memo and fills the the first col
with 0 for use in a semiglobal alignment
row: number of rows
col: number of cols
return: the constructed memo
'''
def buildBlankSemiGlobalMemo(row, col):
    memo = []

    for i in range(row+1):
        memo.append([])
        for j in range(col+1):
            memo[i].append(sys.maxsize)

    memo[0][0] = 0
    for j in range(1,row+1):
        memo[j][0] = 0
    for k in range(1,col+1):
        memo[0][k] = 0 + k * -140

    return memo

'''
Finds the two DNA sequences with the best local alignment score
DNAList: List of DNA to be combined
matMisScoring: 
return: the two strand with the best alignment and their memo
'''
def bestLocalCombination(DNAList, matMisScoring, gapPenalty,
        completedCombinations):
    maxval = -sys.maxsize
    bestcomb = []
    for DNAs in combinations(DNAList, 2):
        if DNAs not in completedCombinations:
            completedCombinations.append(DNAs)
            memo = buildBlankLocalMemo(len(DNAs[0][2]), len(DNAs[1][2]))
            val = calcLocalMemo(len(DNAs[0][2]), len(DNAs[1][2]), 
                    DNAs[0][2], DNAs[1][2], memo, matMisScoring, gapPenalty)
            if val > maxval:
                maxval = val
                bestcomb = [DNAs[0], DNAs[1], memo]

    return bestcomb

'''
Finds the corridinates of the highest score in a local alignment memo
memo: the local memo
return: list of the row and col the highest score corresponds to
'''
def calcHighScoreCord(memo):
    highScoreCord = []
    highScore = 0
    for row in range(len(memo)):
        for col in range(len(memo[row])):
            if memo[row][col] > highScore:
                highScore = memo[row][col]
                highScoreCord = [row, col]

    return highScoreCord

'''
Takes the two aligned portions of the fragments
and combines them under the assumption that
gaps are mistakes from the sequencing process and
that the original nucleotide is found in the other
aligned sequence. If two nucleotides differ in a
position then assuming we do not know which is the
original and which is the mistake we will randomly
choose the one that will be in the consensus aligned
sequence.
'''
def fuseAlignments(align1, align2): 
    alignedseq = ''
    for nuc in range(0, len(align1)):
        if align1[nuc] == '-':
            alignedseq += align2[nuc]
        elif align2[nuc] == '-':
            alignedseq += align1[nuc]
        else:
            flag = randint(1,2)
            if flag == 1:
                alignedseq += align1[nuc]
            elif flag == 2:
                alignedseq += align2[nuc] 
        
    return alignedseq

'''
Combine two contigs
contig1: the first contig
contig2: the second contig
return: the assembled sequence
'''
def assembleContigs(contig1, contig2, align1, align2):
    gaplessAlign1 = removeGaps(align1)
    gaplessAlign2 = removeGaps(align2)
    align1Start = contig1.find(gaplessAlign1)
    align1End = align1Start + len(gaplessAlign1)
    align2Start = contig2.find(gaplessAlign2)
    align2End = align2Start + len(gaplessAlign2)
    fusedAlign = fuseAlignments(align1, align2)
    assembledSeq = ''

    if len(gaplessAlign1) == len(contig1) or len(gaplessAlign2) == len(contig2):
        if len(contig1) > len(contig2):
            assembledSeq = assembleNestedContig(contig1, align1Start, align1End, fusedAlign)
        else:
            assembledSeq = assembleNestedContig(contig2, align2Start, align2End, fusedAlign) 
    elif align1End == len(contig1) and align2Start == 0:
        assembledSeq = assembleEndContigs(contig1, contig2, fusedAlign, align1Start, align2End)
    elif align2End == len(contig2) and align1Start == 0:
        assembledSeq = assembleEndContigs(contig2, contig1, fusedAlign, align2Start, align1End)
    elif (align1Start >= 0) and (align1End <= len(contig1)) and (align2Start >=
            0) and (align2End <= len(contig2)):
        assembledSeq = assembleDoubleNestedContigs(contig1, contig2,
                align1Start, align1End, align2Start, align2End, fusedAlign)
    else:
        assembledSeq = 'flag'

    return assembledSeq
'''
Assemble a contig that is completely contained within another
'''
def assembleNestedContig(contig, alignStart, alignEnd, fusedAlign):
    return contig[:alignStart] + fusedAlign + contig[alignEnd:]
'''
Assemble contigs who's alignments are found at the end and beginning of each
other
'''
def assembleEndContigs(firstContig, secondContig, fusedAlign, firstAlignStart,
        secondAlignEnd):
    return firstContig[:firstAlignStart] + fusedAlign + secondContig[secondAlignEnd:]
'''
Assemble contigs who's alignments are nested within each other
'''
def assembleDoubleNestedContigs(contigOne, contigTwo, alignOneStart,
        alignOneEnd, alignTwoStart, alignTwoEnd, fusedAlign):
    newalignOneStart = 0
    newalignOneEnd = len(contigOne)
    newalignTwoStart = 0
    newalignTwoEnd = len(contigTwo)
    assembledSeq = ''
    if len(contigOne[:alignOneStart]) > len(contigTwo[:alignTwoStart]):
        newalignOneStart = alignOneStart - len(contigTwo[:alignTwoStart])
    elif len(contigOne[:alignOneStart]) < len(contigTwo[:alignTwoStart]):
        newalignTwoStart = alignTwoStart - len(contigOne[:alignOneStart])
    if len(contigOne[alignOneEnd:]) > len(contigTwo[alignTwoEnd:]):
        newalignOneEnd = alignOneEnd + len(contigTwo[alignTwoEnd:])
    elif len(contigOne[alignOneEnd:]) < len(contigTwo[alignTwoEnd:]):
        newalignTwoEnd = alignTwoEnd + len(contigOne[alignOneEnd:])

    newFusedAlign = fuseAlignments(contigOne[newalignOneStart:alignOneStart] +
            fusedAlign + contigOne[alignOneEnd:newalignOneEnd],
            contigTwo[newalignTwoStart:alignTwoStart] + fusedAlign
            +contigTwo[alignTwoEnd:newalignTwoEnd])

    if newalignOneEnd - newalignOneStart == len(contigOne) or newalignTwoEnd - newalignTwoStart == len(contigOne):
        if len(contigOne) > len(contigTwo):
            assembledSeq = assembleNestedContig(contigOne, newalignOneStart,
                    newalignOneEnd, newFusedAlign)
        else:
            assembledSeq = assembleNestedContig(contigTwo, newalignTwoStart,
                    newalignTwoEnd, newFusedAlign) 
    elif newalignOneEnd == len(contigOne) and newalignTwoStart == 0:
        assembledSeq = assembleEndContigs(contigOne, contigTwo, newFusedAlign, newalignOneStart, newalignOneEnd)
    elif newalignTwoEnd == len(contigTwo) and newalignOneStart == 0:
        assembledSeq = assembleEndContigs(contigTwo, contigOne, newFusedAlign, newalignTwoStart, newalignOneEnd)
    
    return assembledSeq


'''
Subroutine that will remove the gaps from the individual aligned sequences
and return the gapless sequence that is contained within the DNA fragment,
which will be used later.
'''
def removeGaps(seq):
    gaplessSeq = seq.replace('-','')
    return gaplessSeq 

'''
Calculates the coverage of an assembled sequence based on the fragments that
assembled it
'''
def calcCoverage(assembledContig, fragmentList, misMatScoring, gapPenalty):
    coverageList = [0] * len(assembledContig)

    for i in fragmentList:
        fragment = i[2]
        print('Calculating coverage of: ' + i[0])
        memo = buildBlankSemiGlobalMemo(len(assembledContig), len(fragment))
        calcSemiGlobalMemo(len(assembledContig), len(fragment), assembledContig,
                fragment, memo, misMatScoring, gapPenalty)
        startIndex = calcHighScoreCord(memo)
        alignments = localBacktrace(memo, assembledContig, fragment, gapPenalty,
                startIndex)
        fragmentStarts = assembledContig.find(removeGaps(alignments[0]))
        print('Fragment starts at: ' + str(fragmentStarts))
        for j in range(fragmentStarts,
                fragmentStarts+len(removeGaps(alignments[0]))-1):
            coverageList[j] += 1

    return mean(coverageList)

def main(DNAFileName):
    sys.setrecursionlimit(100000)
    hoxD55Scoring = {'A': {'A': 91, 'C': -90, 'G': -25, 'T': -100},
                     'C': {'A': -90, 'C': 100, 'G': -100, 'T': -25},
                     'G': {'A': -25, 'C': -100, 'G': 100, 'T': -90},
                     'T': {'A': -100, 'C': -25, 'G': -90, 'T': 91}}
    hoxD70Scoring = {'A': {'A': 91, 'C': -114, 'G': -31, 'T': -123},
                     'C': {'A': -114, 'C': 100, 'G': -125, 'T': -31},
                     'G': {'A': -31, 'C': -125, 'G': 100, 'T': -114},
                     'T': {'A': -123, 'C': -31, 'G': -114, 'T': 91}}
    gapPenalties = [-120, -140]
    hiScoreCords = []
    DNAList = readfasta(DNAFileName)
    tempDNAList = DNAList[:]
    combinationsCalculated = []

    
    for i in range(len(DNAList) - 1):   
        bestCombo = bestLocalCombination(tempDNAList, hoxD70Scoring,
                gapPenalties[1], combinationsCalculated)
        hiScoreCords = calcHighScoreCord(bestCombo[2])
        alignedSeqs = localBacktrace(bestCombo[2], bestCombo[0][2], bestCombo[1][2], gapPenalties[1], hiScoreCords)
        assembledSeq = assembleContigs(bestCombo[0][2], bestCombo[1][2],
                alignedSeqs[0], alignedSeqs[1])
        if assembledSeq == 'flag':
            raise NameError(bestCombo[0][0] + ' and ' + bestCombo[1][0] + ' do not fit into defined assemblies')
        tempDNAList.remove(bestCombo[0])
        tempDNAList.remove(bestCombo[1])
        tempDNAList.append([bestCombo[0][0] + bestCombo[1][0], bestCombo[0][1] +
            bestCombo[1][1], assembledSeq])
        print(tempDNAList[-1][0])
        print(tempDNAList[-1][2] + '\n')
    
    
    averageCoverage = calcCoverage(tempDNAList[-1][2], DNAList, hoxD70Scoring, gapPenalties[1])
    
    print('The fully assembled sequence:\n' + tempDNAList[-1][2])
    print('Coverage: ' + str(int(averageCoverage)))

main('Assignment2Fragments.txt')
