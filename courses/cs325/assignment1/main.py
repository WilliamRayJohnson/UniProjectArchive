from geneTranslation import *
from calcTransmemDom import *

def main(geneFileName, outputFileName):
    geneList = []
    geneList = readfasta(geneFileName)
    outputFile = open(outputFileName, 'w')

    for gene in geneList:
        AASequence = translateGeneDNAToAASequence(gene[2])
        AASequence = AASequence[1:(len(AASequence)-1)]
        transmemDomainsHH = calcTransmemDomainsHH(AASequence)
        transmemDomainsKD = calcTransmemDomainsKD(AASequence)
        outputFile.write(gene[1] + '\n')
        outputFile.write(AASequence + '\n\n')
        outputFile.write('According to hydrophobicity and helicity there are '
                         + str(len(transmemDomainsHH)) +
                         ' that identify as transmembrane domains. They are:\n')
        for TDregion in transmemDomainsHH:
            outputFile.write(AASequence[TDregion[0]:TDregion[1]] + ', ')
        outputFile.write('\n\nAccording to the Kyte Dolittle scale there are '
                         + str(len(transmemDomainsKD)) +
                         ' that identify as transmembrane domains. They are:\n')
        for TDregion in transmemDomainsKD:
            outputFile.write(AASequence[TDregion[0]:TDregion[1]] + ', ')

        outputFile.write('\n\n')

main('assignment1Sequences.txt', 'assignment1Results.txt')
