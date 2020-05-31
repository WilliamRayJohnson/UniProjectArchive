'''
William Ray Johnson
Blake Inderski
Series of functions to translate DNA to its amino acid sequence,
and two functions that calculate transmembrane locations in a gene.
Last modified: 29 September 2016
'''

from readfasta import *
from statistics import mean

'''
Translate a gene, in DNA, to it's coresponding RNA sequence
gene: a gene in its DNA sequence
'''
def translateGeneDNA(gene):
    baseParingsSense = {'G':'G', 'C':'C', 'A':'A', 'T':'U'}
    translatedGene = ''

    for i in gene:
        if i != '\n':
            translatedGene = translatedGene + baseParingsSense[i]

    return translatedGene
'''
Translated a gene, in RNA, to it's coresponding amino acid sequence.
Will stop at a stop codon or the end of the sequence.
translatedGene: a gene in its RNA sequence
'''
def translateGeneRNA(translatedGene):
    aminoAcidSequence = ''
    aminoAcidNucliotideParingLength = 3
    translatedGeneCopy = translatedGene
    i = 0
    stopCodonFound = False
    aminoAcidNucliotideParings = {
            'UCA' : 'S',    # Serine
            'UCC' : 'S',    # Serine
            'UCG' : 'S',    # Serine
            'UCU' : 'S',    # Serine
            'UUC' : 'F',    # Phenylalanine
            'UUU' : 'F',    # Phenylalanine
            'UUA' : 'L',    # Leucine
            'UUG' : 'L',    # Leucine
            'UAC' : 'Y',    # Tyrosine
            'UAU' : 'Y',    # Tyrosine
            'UAA' : '_',    # Stop
            'UAG' : '_',    # Stop
            'UGC' : 'C',    # Cysteine
            'UGU' : 'C',    # Cysteine
            'UGA' : '_',    # Stop
            'UGG' : 'W',    # Tryptophan
            'CUA' : 'L',    # Leucine
            'CUC' : 'L',    # Leucine
            'CUG' : 'L',    # Leucine
            'CUU' : 'L',    # Leucine
            'CCA' : 'P',    # Proline
            'CCC' : 'P',    # Proline
            'CCG' : 'P',    # Proline
            'CCU' : 'P',    # Proline
            'CAC' : 'H',    # Histidine
            'CAU' : 'H',    # Histidine
            'CAA' : 'Q',    # Glutamine
            'CAG' : 'Q',    # Glutamine
            'CGA' : 'R',    # Arginine
            'CGC' : 'R',    # Arginine
            'CGG' : 'R',    # Arginine
            'CGU' : 'R',    # Arginine
            'AUA' : 'I',    # Isoleucine
            'AUC' : 'I',    # Isoleucine
            'AUU' : 'I',    # Isoleucine
            'AUG' : 'M',    # Methionine
            'ACA' : 'T',    # Threonine
            'ACC' : 'T',    # Threonine
            'ACG' : 'T',    # Threonine
            'ACU' : 'T',    # Threonine
            'AAC' : 'N',    # Asparagine
            'AAU' : 'N',    # Asparagine
            'AAA' : 'K',    # Lysine
            'AAG' : 'K',    # Lysine
            'AGC' : 'S',    # Serine
            'AGU' : 'S',    # Serine
            'AGA' : 'R',    # Arginine
            'AGG' : 'R',    # Arginine
            'GUA' : 'V',    # Valine
            'GUC' : 'V',    # Valine
            'GUG' : 'V',    # Valine
            'GUU' : 'V',    # Valine
            'GCA' : 'A',    # Alanine
            'GCC' : 'A',    # Alanine
            'GCG' : 'A',    # Alanine
            'GCU' : 'A',    # Alanine
            'GAC' : 'D',    # Aspartic Acid
            'GAU' : 'D',    # Aspartic Acid
            'GAA' : 'E',    # Glutamic Acid
            'GAG' : 'E',    # Glutamic Acid
            'GGA' : 'G',    # Glycine
            'GGC' : 'G',    # Glycine
            'GGG' : 'G',    # Glycine
            'GGU' : 'G'}    # Glycine

    while (not(stopCodonFound) and
           i < int(len(translatedGene)/aminoAcidNucliotideParingLength)):
        i += 1
        currentAminoAcid = aminoAcidNucliotideParings[
            translatedGeneCopy[:aminoAcidNucliotideParingLength]]
        if currentAminoAcid == '_':
            stopCodonFound = True
        aminoAcidSequence = aminoAcidSequence + currentAminoAcid
        translatedGeneCopy = translatedGeneCopy[aminoAcidNucliotideParingLength:]

    return aminoAcidSequence

'''
Translate a gene in DNA to RNA then to amino acids
geneSequence: a sequence of DNA
Return: the amino acid sequences
'''
def translateGeneDNAToAASequence(geneSequence):
    translatedGene = translateGeneDNA(geneSequence)
    aminoAcidSequence = translateGeneRNA(translatedGene)
    
    return aminoAcidSequence
'''
Calculates number of transmembrane domains based on hydrophobicity and helicity.
AASequence: gene's amino acid sequence NOT including methionine (start codon) or
the '_' (stop codon)
Return: a list containing the indices of regioins identified as transmembrane
domains
'''
def calcTransmemDomainsHH(AASequence):
    #amino acid fasta letter : [hydrophobicity scale, helicity scale]
    aminoAcidScale = {
        'F' : [5.00, 1.26], 'W' : [4.88, 1.07], 'L' : [4.76, 1.28],
        'I' : [4.41, 1.29], 'M' : [3.23, 1.22], 'V' : [3.02, 1.27],
        'C' : [2.50, 0.79], 'Y' : [2.00, 1.11], 'A' : [0.16, 1.24],
        'T' : [-1.08, 1.09], 'E' : [-1.50, 0.85], 'D' : [-2.49, 0.89],
        'Q' : [-2.76, 0.96], 'R' : [-2.77, 0.95], 'S' : [-2.85, 1.00],
        'G' : [-3.31, 1.15], 'N' : [-3.79, 0.94], 'H' : [-4.63, 0.97],
        'P' : [-4.92, 0.57], 'K' : [-5.00, 0.88] }

    windowLength = 19
    hydrophobicThresh = 0.4
    helicityThresh = 1.1
    #[[sequence of hydrophobicity scales], [sequence of helicity scales]]
    AASeqScaleList = [[],[]]
    transmemIndices = []

    for aa in AASequence:
        AASeqScaleList[0].append(aminoAcidScale[aa][0])
        AASeqScaleList[1].append(aminoAcidScale[aa][1])

    while len(AASeqScaleList[0]) >= windowLength:
        hydrophoAvg = 0
        helicityAvg = 0

        hydrophoAvg = mean(AASeqScaleList[0][:windowLength])
        if hydrophoAvg > hydrophobicThresh:
            
            helicityAvg = mean(AASeqScaleList[1][:windowLength])
            if helicityAvg > helicityThresh:
                transmemFirstIndex = len(AASequence)-len(AASeqScaleList[0])
                transmemIndices.append([transmemFirstIndex, transmemFirstIndex
                                        + windowLength])
                AASeqScaleList = [AASeqScaleList[0][windowLength:],
                                  AASeqScaleList[1][windowLength:]]
            else:
                AASeqScaleList = [AASeqScaleList[0][1:], AASeqScaleList[1][1:]]
        else:
            AASeqScaleList = [AASeqScaleList[0][1:], AASeqScaleList[1][1:]]

    return transmemIndices
'''
Calculates number of transmembrane domains based on Kyte Doolittle scale.
AASequence: gene's amino acid sequence NOT including methionine (start codon)
or the '_' (stop codon)
Return: a list containing the indices of regioins identified as transmembrane
domains
'''
def calcTransmemDomainsKD(AASequence):
    aminoAcidScale = {
        'F' : 2.8, 'W' : -0.9, 'L' : 3.8, 'I' : 4.5, 'M' : 1.9, 'V' : 4.2,
        'C' : 2.5, 'Y' : -1.3, 'A' : 1.8, 'T' : -0.7, 'E' : -3.5, 'D' : -3.5,
        'Q' : -3.5, 'R' : -4.5, 'S' : -0.8, 'G' : -0.4, 'N' : -3.5, 'H' : -3.2,
        'P' : -1.6, 'K' : -3.9 }

    windowLength = 19
    KDThresh = 1.6
    AASeqScaleList = []
    transmemIndices = []

    for aa in AASequence:
        AASeqScaleList.append(aminoAcidScale[aa])

    while len(AASeqScaleList) >= windowLength:
        KDAvg = 0
        KDAvg = mean(AASeqScaleList[:windowLength])
        if KDAvg > KDThresh:
            transmemFirstIndex = len(AASequence)-len(AASeqScaleList)
            transmemIndices.append([transmemFirstIndex, transmemFirstIndex
                                        + windowLength])
            AASeqScaleList = AASeqScaleList[windowLength:]
        else:
            AASeqScaleList = AASeqScaleList[1:]

    return transmemIndices

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
