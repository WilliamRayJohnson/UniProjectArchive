'''
A program to calculate transversion vs translation rates in a set of aligned
sequences
William Ray Johnson
Last Edited: 3 December 2017
'''

from readfasta import *

def calcTranslationTransversionRates(sequences):
    translationCount = 0
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
            mutationType = isTransversionOrTranslation(nucsAtCurrentSite)
            if mutationType == 'translation':
                translationCount += 1
            elif mutationType == 'transversion':
                transversionCount += 1

    mutationCount = translationCount + transversionCount
    translationRate = (float(translationCount)/float(mutationCount)) * 100
    transversionRate = (float(transversionCount)/float(mutationCount)) * 100

    return [translationRate, transversionRate]

def isEqualSite(nucs):
    return nucs[1:] == nucs[:-1]

def isTransversionOrTranslation(listOfNucs):
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
        return 'translation'
    elif foundCytosine and foundGuanine or foundCytosine and foundAdenine or foundThymine and foundGuanine or foundThymine and foundAdenine:
        return 'transversion'
    elif sum([foundCytosine, foundThymine, foundGuanine, foundAdenine]) > 2:
        return 'mixed'
    else:
        raise NameError('No nucleotides found')

def main():
    sequences = readfasta('transversionVsTranslationTest.fasta')

    rates = calcTranslationTransversionRates(sequences)

    print('The percentage of translations is {}%'.format(rates[0]))
    print('The percentage of transversions is {}%'.format(rates[1]))

if __name__ == '__main__':
    main()
        
        
