'''
William Ray Johnson
Functions for calculation of transmembrane domains
Last modified: 27 September 2016
'''

from statistics import mean

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
    #amino acid fasta letter : [hydrophobicity scale, helicity scale]
    aminoAcidScale = {
        'F' : 2.8, 'W' : -0.9, 'L' : 3.8, 'I' : 4.5, 'M' : 1.9, 'V' : 4.2,
        'C' : 2.5, 'Y' : -1.3, 'A' : 1.8, 'T' : -0.7, 'E' : -3.5, 'D' : -3.5,
        'Q' : -3.5, 'R' : -4.5, 'S' : -0.8, 'G' : -0.4, 'N' : -3.5, 'H' : -3.2,
        'P' : -1.6, 'K' : -3.9 }

    windowLength = 19
    KDThresh = 1.788
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
