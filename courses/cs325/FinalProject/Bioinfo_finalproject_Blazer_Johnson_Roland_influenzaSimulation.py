'''
A simulation of Influenza B Hemagglutinin evolution
William Ray Johnson
Carrie Blazer
Robert Roland
Last Edited: 7 December 2016
'''

from readfasta import *
import random
import numpy
import copy
import math

'''
Determines the mutations of a given sequence based on the Kimura 2 Parameter 
model
sequence: the original sequence
pTransi: probability of a transition
pTransv: probability of a transversion
mutationSites: the sites that were mutated
return: the mutated sequence
'''
def kimura2Parameter(sequence, pTransi, pTransv, mutationSites):
    mutationMatrix = {'A':[1-pTransi-(2*pTransv), pTransv, pTransi, pTransi],
                      'C':[pTransv, 1-pTransi-(2*pTransv), pTransv, pTransi],
                      'G':[pTransi, pTransv, 1-pTransi-(2*pTransv), pTransv],
                      'T':[pTransi, pTransi, pTransv, 1-pTransi-(2*pTransv)]}

    for site in range(len(sequence)):
        newNuc = findMutation(mutationMatrix[sequence[site]])
        if newNuc != sequence[site]:
            mutationSites.append(site)
        sequence = sequence[:site] + newNuc + sequence[site+1:]
            
    return sequence

'''
Determines what nucleotide a site should have given transition and transversion
rates
mutationProbs: the probability of [A, C, G, T] for a nucleotide
return: the 'new' nucleotide
'''
def findMutation(mutationProbs):
    adenineEnd = mutationProbs[0]
    cytosineEnd = adenineEnd + mutationProbs[1]
    guanineEnd = cytosineEnd + mutationProbs[2]
    thymineEnd = guanineEnd + mutationProbs[3]
    nucleoOrder = ['A', 'C', 'G', 'T']
    randomNum = random.random()

    if randomNum <= adenineEnd:
        return nucleoOrder[0]
    elif randomNum > adenineEnd and randomNum <= cytosineEnd:
        return nucleoOrder[1]
    elif randomNum > cytosineEnd and randomNum <= guanineEnd:
        return nucleoOrder[2]
    elif randomNum > guanineEnd and randomNum <= thymineEnd:
        return nucleoOrder[3]
    else:
        raise NameError('Random Number not in range')

'''
Translated a gene, in DNA, to it's coresponding amino acid sequence.
Will stop at a stop codon or the end of the sequence.
DNASeq: a gene in its DNA sequence
'''
def translateDNA(DNASeq):
    aminoAcidSequence = ''
    aminoAcidNucleotideParingLength = 3
    DNASeqCopy = DNASeq
    i = 0
    stopCodonFound = False
    aminoAcidNucleotideParings = {
            'TCA' : 'S',    # Serine
            'TCC' : 'S',    # Serine
            'TCG' : 'S',    # Serine
            'TCT' : 'S',    # Serine
            'TTC' : 'F',    # Phenylalanine
            'TTT' : 'F',    # Phenylalanine
            'TTA' : 'L',    # Leucine
            'TTG' : 'L',    # Leucine
            'TAC' : 'Y',    # Tyrosine
            'TAT' : 'Y',    # Tyrosine
            'TAA' : '_',    # Stop
            'TAG' : '_',    # Stop
            'TGC' : 'C',    # Cysteine
            'TGT' : 'C',    # Cysteine
            'TGA' : '_',    # Stop
            'TGG' : 'W',    # Tryptophan
            'CTA' : 'L',    # Leucine
            'CTC' : 'L',    # Leucine
            'CTG' : 'L',    # Leucine
            'CTT' : 'L',    # Leucine
            'CCA' : 'P',    # Proline
            'CCC' : 'P',    # Proline
            'CCG' : 'P',    # Proline
            'CCT' : 'P',    # Proline
            'CAC' : 'H',    # Histidine
            'CAT' : 'H',    # Histidine
            'CAA' : 'Q',    # Glutamine
            'CAG' : 'Q',    # Glutamine
            'CGA' : 'R',    # Arginine
            'CGC' : 'R',    # Arginine
            'CGG' : 'R',    # Arginine
            'CGT' : 'R',    # Arginine
            'ATA' : 'I',    # Isoleucine
            'ATC' : 'I',    # Isoleucine
            'ATT' : 'I',    # Isoleucine
            'ATG' : 'M',    # Methionine
            'ACA' : 'T',    # Threonine
            'ACC' : 'T',    # Threonine
            'ACG' : 'T',    # Threonine
            'ACT' : 'T',    # Threonine
            'AAC' : 'N',    # Asparagine
            'AAT' : 'N',    # Asparagine
            'AAA' : 'K',    # Lysine
            'AAG' : 'K',    # Lysine
            'AGC' : 'S',    # Serine
            'AGT' : 'S',    # Serine
            'AGA' : 'R',    # Arginine
            'AGG' : 'R',    # Arginine
            'GTA' : 'V',    # Valine
            'GTC' : 'V',    # Valine
            'GTG' : 'V',    # Valine
            'GTT' : 'V',    # Valine
            'GCA' : 'A',    # Alanine
            'GCC' : 'A',    # Alanine
            'GCG' : 'A',    # Alanine
            'GCT' : 'A',    # Alanine
            'GAC' : 'D',    # Aspartic Acid
            'GAT' : 'D',    # Aspartic Acid
            'GAA' : 'E',    # Glutamic Acid
            'GAG' : 'E',    # Glutamic Acid
            'GGA' : 'G',    # Glycine
            'GGC' : 'G',    # Glycine
            'GGG' : 'G',    # Glycine
            'GGT' : 'G'}    # Glycine

    while (not(stopCodonFound) 
            and i < int(len(DNASeq)/aminoAcidNucleotideParingLength)):
        i += 1
        currentAminoAcid = aminoAcidNucleotideParings[
            DNASeqCopy[:aminoAcidNucleotideParingLength]]
        if currentAminoAcid == '_':
            stopCodonFound = True
        aminoAcidSequence = aminoAcidSequence + currentAminoAcid
        DNASeqCopy = DNASeqCopy[aminoAcidNucleotideParingLength:]

    return aminoAcidSequence


'''
Finds the interval in which a value is located on
value: the value in question
partition: the start/end points of the intervals
return: the index of the interval the values was located at, returns -1 if
value not found on any interval
'''
def findInterval(value, partition):
    for sec in range(0, len(partition)):
        if value < partition[sec]:
            return sec - 1
    return -1

'''
Calculates the fitness of a newly mutated sequence based on the mutations
located in antigenicSites
aaSeq: the original sequence
aaNewSeq: the newly mutated sequence
mutationSites: the sites the mutations took place
antigenicSites: the range of sites of antigenic regions
return: the fitness of the sequence
'''
def calcFitness(aaSeq, aaNewSeq, mutationSites, antigenicSites):
    fitness = 1
    #Check to see if there were mutations that were not silent
    if aaSeq != aaNewSeq:
        for mutSite in mutationSites:
            muteCodon = int(mutSite/3)
            #Check if mutation was silent
            if aaSeq[muteCodon] != aaNewSeq[muteCodon]:
                for antigenicSite in antigenicSites:
                    if(muteCodon >= antigenicSite[0] and muteCodon <=
                            antigenicSite[1]):
                        fitness += 2
    return fitness

'''
generates a new population of a given size based on the given sequences.
Sequences with a higher fitness have a greater probability of making it to the
next generation
muteSeqs: the population of mutated sequences
popSize: the size of the population
return: the new population
'''
def generateNewPop(muteSeqs, popSize):
    newPopulation = []
    cumuWeights = [0] + list(numpy.cumsum([muteSeq[2] for muteSeq in muteSeqs]))

    for member in range(popSize):
        randomSeq = random.uniform(0, cumuWeights[-1])
        seqIndex = findInterval(randomSeq, cumuWeights)
        newPopulation.append(copy.deepcopy(muteSeqs[seqIndex]))

    return newPopulation

'''
Determines if there is mutation, and if there is mutates the sequence based on
the Kimura 2 Parameter. Calculates fitness afterwards
seq: the original sequence
lineage: the lineage the sequence is from
mutationRate: the rate of mutation
transiTransvRates: list of transition and transversion rates
antigenicSites: the ranges of antigenic sites
return: [Newly mutated sequence, source lineage, its calculated fintess]
if no mutation [original sequence, source lineage, fitness of 1 since no
mutation]
'''
def mutateSequence(seq, lineage, mutationRate, transiTransvRates,
        antigenicSites):
    isMutation = random.random()
    mutationSites = []
    fitness = 0
    if isMutation > math.pow((1 - mutationRate), len(seq)):
        aaSeq = translateDNA(seq)
        newSeq = kimura2Parameter(seq, mutationRate *
                        transiTransvRates[0], mutationRate *
                        transiTransvRates[1], mutationSites)
        aaNewSeq = translateDNA(newSeq)
        #Checks to see if new sequence starts with a start codon and ends 
        #with a stop codon in the same location
        if len(aaSeq) == len(aaNewSeq) and aaNewSeq[0] == 'M':
            fitness = calcFitness(aaSeq, aaNewSeq, mutationSites, antigenicSites)
            return [newSeq, lineage, fitness]
        #If start or stop codon changed return none
        else:
            return None
    else:
        return [seq, lineage, 1]
'''
Simulates the mutation of influenza B hemagglutinin of a given population 
size over a givennumber of years. Begins with an equal number of each lineage
populationSize: the size of the population
years: the number of years the simulation will run
originalYamagata: the original yamagata sequence
originalVictoria: the original victoria sequence
mutationRates: yamagata and victoria lineage's mutation rate
transiTransvRates: the rate of transition and transversion
yamagataAntigenicSites: location of the yamagata lineage's antigenic sites
victoriaAntigenicSites: location of the victoria lineage's antigenic sites
return: a list of the populations after each year
'''
def influenzaBSimulation(populationSize, years, origYamagata, origVictoria,
        mutationRates, transiTransvRates, yamagataAntigenicSites,
        victoriaAntigenicSites):
    yamagataPop = []
    victoriaPop = []
    yearlyPopulations = []
    for aCopy in range(int(populationSize/2)):
        yamagataPop.append(copy.deepcopy(origYamagata))
        victoriaPop.append(copy.deepcopy(origVictoria))

    for year in range(years):
        newSeqs = []
        yamaSeqCount = 0
        victSeqCount = 0
        #Simulate yamagata sequences mutation
        for yamaSeq in yamagataPop:
            muteYamaSeq = mutateSequence(yamaSeq[2], 'Y', mutationRates[0],
                transiTransvRates, yamagataAntigenicSites)
            if muteYamaSeq != None:
                newSeqs.append(muteYamaSeq)
        #Simulate victoria sequences mutation
        for victSeq in victoriaPop:
            muteVictSeq = mutateSequence(victSeq[2], 'V', mutationRates[1],
                transiTransvRates, victoriaAntigenicSites)
            if muteVictSeq != None:
                newSeqs.append(muteVictSeq)
        #set up the new population
        newPop = generateNewPop(newSeqs, populationSize)
        #old populations now irrelevant, so reset
        yamagataPop = []
        victoriaPop = []
        for seq in newPop:
            if seq[1] == 'Y':
                name = 'Yamagata/{}/{}'.format(year, yamaSeqCount)
                yamagataPop.append([name,'>' + name, seq[0]])
                yamaSeqCount += 1
            elif seq[1] == 'V':
                name = 'Victoria/{}/{}'.format(year, victSeqCount)
                victoriaPop.append([name, '>' + name, seq[0]])
                victSeqCount += 1

        yearlyPopulations.append(yamagataPop + victoriaPop)

        yPercentage = float(yamaSeqCount / (yamaSeqCount + victSeqCount)) * 100.0
        vPercentage = float(victSeqCount / (yamaSeqCount + victSeqCount)) * 100.0
        print('Year {}'.format(year))
        print('Yamagata: {}%'.format(yPercentage))
        print('Victoria: {}%\n'.format(vPercentage))

    return yearlyPopulations

def main():
    sequences = readfasta('originalSequences.fasta')
    mutationRates = [0.00241, 0.00139]
    transiTransvRates = [0.77, 0.23]
    yamaAntigenicSites = [[116,137], [141,150], [162,167], [195,203]]
    victAntigenicSites = [[116,137], [141,150], [162,167], [197,205]]
    totalPopulation = 100
    simulationYears = 10
    data = influenzaBSimulation(totalPopulation, simulationYears, sequences[0], sequences[1],
            mutationRates, transiTransvRates, yamaAntigenicSites,
            victAntigenicSites)

    #generates output file for use in a phylogenetic tree
    outputFile = open('dataForTree.fasta', 'w')
    yearlySampleSize = 5
    yearCounter = 0
    for year in data:
        for sample in range(yearlySampleSize):
            randomSeq = random.choice(year)
            outputFile.write(str(randomSeq[1]) + '\n')
            outputFile.write(str(randomSeq[2]) + '\n')
            outputFile.write('\n')
        yearCounter += 1
        
if __name__ == '__main__':
    main()
