/**
 * The purpose of this file is to analyze an algorithm that finds the largest
 * power of 2 smaller than or equal to a non-negative integer n.
 * Code used for getting values from command line taken from Jon Beck's example
 * program.
 * @version February 8, 2016
 * @author William Ray Johnson
 */
typedef unsigned int uint;

#include <iostream>
#include <sstream>

using namespace std;

uint largestPower(uint n, uint & basicOps);

int main( int argc, char * argv [] )
{
    if( argc != 2 )
    {
        cerr << "Usage: " << argv[0] << " n where n is the number of elements" << endl;
        return 1;
    }

    stringstream ss( argv[ 1 ]);
    uint n = 0;
    ss >> n;
    
    uint power;
    uint basicOps = 0;
    uint & basicOpsRef = basicOps;

    power = largestPower( n, basicOpsRef );
    cout << "The highest power of 2 in " << n << " is " << power << "." << endl;
    cerr << n << '\t' << basicOps << endl;

    return 0;
}

/**
 * This function will find the largest power of 2 less than or equal to a
 * non-negative integer n and count the number of basic operations.
 * @param n The integer that will be operated on.
 * @param basicOps A reference to a counter of the number of basic operations
 * this function performs.
 * @return Largest power of 2 less than or equal to the input n.
 */
uint largestPower(uint n, uint & basicOps)
{
    uint i = n;
    uint j = i & ( i - 1 );
    while( j != 0 )
    {
        i = j;
        j = i & ( i - 1 );
        basicOps++;
    }

    return i;
}
