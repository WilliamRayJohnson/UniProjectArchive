/**
 * nqueens
 * @author Jon Beck
 * @author William Ray Johnson
 */
typedef unsigned int uint;
#include <cassert>
#include <sstream>
#include <iostream>
#include <cstdlib>
#include <iomanip>
#include "matrix.h"
#include <vector>
using namespace std;

/**
 * This function checks to see if the most recently placed queen has any
 * conflicts with any of the queens already placed on the board. It only checks
 * the diagonals, as it would be impossible for a queen to be on the same row or
 * column as another.
 * @param board the n x n chessboard
 * @param cur_row the row of the last queen placed
 * @param cur_col the column of the last queen placed
 * @return the absence of a conflict with this board arrangement
 */
bool ok( const Matrix< bool > & board, uint cur_row, uint cur_col )
{
    uint n = board.numrows();

    for( uint row = 0; row < n; row++ )
        for( uint col = 0; col < n; col++ )
        {
            if( board.at(row, col) && board.at(cur_row, cur_col) &&
                    !(row == cur_row && col == cur_col ))
            {
                if( n - col + row == n - cur_col + cur_row )
                    return false;
                if( row + col + 1 == cur_row + cur_col + 1 )
                    return false;
            }
        }

    return true;
}

/**
 * a simple procedure to output an ASCII art horizontal rule with plus
 * signs where columns will intersect
 * @param cols the number of columns
 */
void hr( uint cols )
{
    cout << "    +";
    for( uint col = 0; col < cols; col++ )
        cout << "---+";
    cout << endl;
}

/**
 * this function dumps the board to std output
 * @param board the board whose arrangement to dump
 * @param solu_count the number of the solution being printed
 * @param num_of_nodes the number of nodes it took to get to this solution
 */
void printBoard( const Matrix< bool> & board, uint solu_count, uint num_of_nodes )
{
    cout << "Solution " << solu_count << endl;
    cout << "Number of Nodes for solution: " << num_of_nodes << endl;
    hr( board.numrows());
    for( uint row = 0; row < board.numrows(); row++)
    {
        cout << ' ' << setw(2) << board.numrows() - row << " |";
        for( uint col = 0; col < board.numrows(); col++ )
        {
            if( board.at(row, col) )
                cout << " X |";
            else
                cout << "   |";
        }
        cout << endl;
        hr( board.numrows());
    }

    cout << "     ";
    for( uint col = 0; col < board.numrows(); col++ )
        cout << ' ' << (char)( 'a' + col ) << "  ";
    cout << endl << endl;
}

/**
 * This is the recursive backtracking function.  When called, k queens
 * have already been placed on the board in columns 0 .. k-1.  We're
 * trying to place the next queen in column k.
 * @param rows_in_use the rows that are in use by queens
 * @param k the column in which to place the current queen
 * @param solu_count the number of solutions that have been found
 * @param num_of_nodes the number of nodes traversed 
 * @param board the board on which to place the queen
 */
void r_backtrack( vector< bool > & rows_in_use, uint k, uint & solu_count, uint & num_of_nodes, Matrix< bool > & board )
{
    if( k == board.numrows() )
    {
        solu_count++;
        printBoard( board, solu_count, num_of_nodes );
        return;
    }

    for( uint row = 0; row < board.numrows(); row++ )
    {
        if( rows_in_use.at(row) == false )
        {
            board.at(row, k) = true;
            rows_in_use.at(row) = true;
            num_of_nodes++;

            if( ok( board, row, k ))
            {
                r_backtrack( rows_in_use, k + 1, solu_count, num_of_nodes, board );
            }
            board.at(row, k) = false;
            rows_in_use.at(row) = false;
        }
    }
}

int main( int argc, char* argv[] )
{
    if( argc != 2 )
    {
        cout << "Usage: " << argv[0] << " n" << endl;
        cout << "       where n is the number of queens to place" << endl;
        cout << "       on an n x n chessboard, with 0 < n < 26" << endl;
        exit( 2 );
    }
    assert (argc == 2);
    stringstream ss( argv[ 1 ]); 
    uint n;
    ss >> n;
    assert (n > 0 && n < 26);

    Matrix< bool > board( n, n );
    for( uint row = 0; row < n; row++)
        for( uint col = 0; col < n; col++ )
            board.at(row, col) = false;

    uint solu_count = 0;
    uint num_of_nodes = 0;
    vector< bool > rows_in_use (n);
    r_backtrack( rows_in_use, 0, solu_count, num_of_nodes, board );
    if( solu_count == 0 )
        cout << "No solution" << endl;
    exit( 1 );
}
