'''
William Ray Johnson
Blake Inderski
Series of functions to translate DNA to its amino acid sequence
Last modified: 27 September 2016
'''

from datetime import datetime
from readfasta import *

'''
Translate a gene, in DNA, to it's coresponding RNA sequence
gene: a gene in its DNA sequence
'''
def translateGeneDNA(gene):
    baseParingsNonSense = {'G':'C', 'C':'G', 'A':'U', 'T':'A'}
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
