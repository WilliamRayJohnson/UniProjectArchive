import sys
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
Calculates coverage
reassembledCont:
listFragments:
'''
'''
Finds the optimal semiglobal alignment by calculating a memo using dynamic programming.
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

    score = max(calcSemiGlobalMemo(row-1, col, DNA1, DNA2, memo, matMisScoring, gapPenalty) + gapPenalty,
                calcSemiGlobalMemo(row, col-1, DNA1, DNA2, memo, matMisScoring, gapPenalty) + gapPenalty,
                calcSemiGlobalMemo(row-1, col-1, DNA1, DNA2, memo, matMisScoring, gapPenalty) 
                                + matMisScoring[DNA1[row-1]][DNA2[col-1]])
   
    memo[row][col] = score


    return memo[row][col]

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
Finds the corridinates of the highest score in the last column for a semiglobal alignment memo 
memo: the semiglobal memo
return: list of the row and col the highest score corresponds to
'''
def calcHighScoreCord(memo):
    highScoreCord = []
    highScore = 0
    for row in range(len(memo)):
         if memo[row][len(memo[row])-1] > highScore:
            highScore = memo[row][len(memo[row])-1]
            highScoreCord = [row, len(memo[row])-1]

    return highScoreCord


def calcCoverage (reassembledCont,listFragments):
    coverageList = [0]* len(reassembledCont)

    for i in listFragments:
        fragment = i[2]

        memo = buildBlankSemiGlobalMemo(len(reassembledCont),len(fragment))
        

        calcSemiGlobalMemo(len(reassembledCont),len(fragment), reassembledCont, fragment, memo,
                           {'A': {'A': 91, 'C': -114, 'G': -31, 'T': -123},
                     'C': {'A': -114, 'C': 100, 'G': -125, 'T': -31},
                     'G': {'A': -31, 'C': -125, 'G': 100, 'T': -114},
                     'T': {'A': -123, 'C': -31, 'G': -114, 'T': 91}}, -140)

        startIndex = calcHighScoreCord(memo)

        aligned = localBacktrace(memo, reassembledCont, fragment, -140, startIndex)

        contigAlign = aligned[0]

        firstPos = reassembledCont.find(contigAlign)

        for j in range(firstPos, firstPos+len(contigAlign)):
            coverageList[j]+=1
        sum = 0
    
    for k in coverageList:
        
        sum +=k
        
    average = sum/len(coverageList)
    print(average)
    return average 

def main():
    calcCoverage('GACTTCAG', [[0,0,'GACT'],[0,0,'TCAG'],[0,0,'CTTC'],[0,0,'GA'],[0,0,'AG']])

main()
        


        

        
        


