'''
A program to calculate transversion vs transition rates in a set of aligned
sequences
William Ray Johnson
Carrie Blazer
Robert Roland
Last Edited: 7 December 2016
'''

from readfasta import *

'''
Calculates the rate of transitions and transversions
sequences: the sequences to analyze
return: transition rate, transversion rate
'''
def calcTransitionTransversionRates(sequences):
    transitionCount = 0
    transversionCount = 0

    for site in range(0, len(sequences[0][2])):
        nucsAtCurrentSite = []
        gapFound = False
        seqCount = 0
        while seqCount < len(sequences) and not gapFound:
            if sequences[seqCount][2][site] == '-':
                gapFound = True
            else:
                nucsAtCurrentSite.append(sequences[seqCount][2][site])
            seqCount += 1

        if not gapFound and not isEqualSite(nucsAtCurrentSite):
            mutationType = isTransversionOrTransition(nucsAtCurrentSite)
            if mutationType == 'transition':
                transitionCount += 1
            elif mutationType == 'transversion':
                transversionCount += 1

    mutationCount = transitionCount + transversionCount
    transitionRate = (float(transitionCount)/float(mutationCount)) * 100
    transversionRate = (float(transversionCount)/float(mutationCount)) * 100

    return [transitionRate, transversionRate]

'''
Checks to see if nucleotides at a particular site are equal
nucs: the nucliotides at a particular site
return: true if all sites are the same
'''
def isEqualSite(nucs):
    return nucs[1:] == nucs[:-1]

'''
Checks to see if a transition or transversion occured at a particular site. 
Only considers sites that contain two nucleotides
listOfNucs: the list of nucleotide
return: type of mutation or mixed if there were more than 2 nucleotides
'''
def isTransversionOrTransition(listOfNucs):
    foundCytosine = False
    foundThymine = False
    foundGuanine = False
    foundAdenine = False
    nucCount = 0
    
    while sum([foundCytosine, foundThymine, foundGuanine, foundAdenine]) <= 2 and nucCount < len(listOfNucs):
        
        if listOfNucs[nucCount] == 'C':
            foundCytosine = True
        elif listOfNucs[nucCount] == 'T':
            foundThymine = True
        elif listOfNucs[nucCount] == 'G':
            foundGuanine = True
        elif listOfNucs[nucCount] == 'A':
            foundAdenine = True
        else:
            raise NameError('Not a nucleodie')

        nucCount += 1

    if foundCytosine and foundThymine or foundGuanine and foundAdenine:
        return 'transition'
    elif foundCytosine and foundGuanine or foundCytosine and foundAdenine or foundThymine and foundGuanine or foundThymine and foundAdenine:
        return 'transversion'
    elif sum([foundCytosine, foundThymine, foundGuanine, foundAdenine]) > 2:
        return 'mixed'
    else:
        raise NameError('No nucleotides found')

def main():
    sequences = readfasta('transversionVsTransitionTest.fasta')

    rates = calcTransitionTransversionRates(sequences)

    print('The percentage of transitions is {}%'.format(rates[0]))
    print('The percentage of transversions is {}%'.format(rates[1]))

if __name__ == '__main__':
    main()
    
        
