from geneTranslation import *
from calcTransmemDom import *

def test():
    aaList = translateGeneDNAToAASequence('assignment1Sequences.txt')
    transmemDomCounts = []

    for i in range(len(aaList)):
        transmemDomCounts.append(
            calcTransmemDomains(aaList[i][2][1:(len(aaList[i][2])-1)]))

    return transmemDomCounts
