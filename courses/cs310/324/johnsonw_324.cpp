/**
 * Adapted from Weiss 4th page 399
 * dijkstra's algorithm
 * @author Jon Beck
 * @author William Ray Johnson
 * @version March 23, 2016
 */

typedef unsigned int uint;

#include <iostream>
#include <vector>
#include <climits>
#include <list>
#include <queue>

using namespace std;

/**
 * create a class that can comapre two paris by their second element. boolean
 * operator is reversed because priority_queue creates a max-heap by default
 */
class ComparePair
{
    public:
        bool operator() ( const pair<uint, uint> & a, 
                const pair<uint, uint> & b )
        {
            return a.second > b.second;
        }
};

void tracePath( uint startNode, const vector<uint> & path );
void dijkstra( uint startNode, const vector<list<pair<uint, uint> > > & graph );

int main()
{
    vector<list<pair<uint, uint> > > graph;

    // the 0 position is an unused dummy, Weiss starts nodes at 1
    // in fact, this is just like an unreachable node
    list<pair<uint, uint> > v0list;
    graph.push_back( v0list );

    list<pair<uint, uint> > v1list;
    v1list.push_back( make_pair( 2, 2 ) );
    v1list.push_back( make_pair( 4, 1 ) );
    graph.push_back( v1list );

    list<pair<uint, uint> > v2list;
    v2list.push_back( make_pair( 4, 3 ) );
    v2list.push_back( make_pair( 5, 10 ) );
    graph.push_back( v2list );

    list<pair<uint, uint> > v3list;
    v3list.push_back( make_pair( 1, 4 ) );
    v3list.push_back( make_pair( 6, 5 ) );
    graph.push_back( v3list );

    list<pair<uint, uint> > v4list;
    v4list.push_back( make_pair( 3, 2 ) );
    v4list.push_back( make_pair( 5, 2 ) );
    v4list.push_back( make_pair( 6, 8 ) );
    v4list.push_back( make_pair( 7, 4 ) );
    graph.push_back( v4list );

    list<pair<uint, uint> > v5list;
    v5list.push_back( make_pair( 7, 6 ));
    graph.push_back( v5list );

    list<pair<uint, uint> > v6list;
    graph.push_back( v6list );

    list<pair<uint, uint> > v7list;
    v7list.push_back( make_pair( 6, 1 ) );
    graph.push_back( v7list );

    dijkstra( 1, graph );

    return 0;
}

/**
 * Prints path based on passed vector
 * @param startNode the starting point of path
 * @param path the path in vector form
 */
void tracePath( uint startNode, const vector<uint> & path )
{
    if( path.at( startNode ) != 0 )
    {
        cout << path.at( startNode ) << " ";
        tracePath( path.at( startNode ), path );
    }
}   

/**
 * Performs Dijkstra's Algorithm
 * @param startNode the starting node
 * @param graph the graph that the algorithm will be performed on
 */
void dijkstra( uint startNode, const vector<list<pair<uint, uint> > > & graph )
{
    priority_queue< pair<uint, uint>, 
        vector<pair<uint, uint> >, ComparePair > pq;
    vector<uint> dist;
    vector<uint> path;
    vector<bool> known;
    path.resize( graph.size() );

    for( uint i = 0; i < graph.size(); i++ )
    {
        dist.push_back( UINT_MAX );
        known.push_back( false );
    }

    dist.at( startNode ) = 0;
    pq.push( make_pair( startNode, 0 ) );

    while( !pq.empty() )
    {
        pair<uint, uint> v = pq.top();
        pq.pop();
        
        if( !known.at( v.first ) )
        {
            known.at( v.first ) = true;

            for( auto w = graph.at( v.first ).begin(); 
                    w != graph.at( v.first ).end(); ++w )
            {
                if( !known.at( w->first ) )
                {
                    uint cvw = w->second;

                    if( v.second + cvw < dist.at( w->first ) )
                    {
                        pq.push( make_pair( w->first, v.second + cvw ) );
                        dist.at( w->first ) = v.second + cvw;
                        path.at( w->first ) = v.first;
                    }
                }
            }
        }
    }


    cout << "The distance from " << startNode << " to:" << endl;
    for( uint i = 1; i < graph.size(); i++)
    {
        cout << " " << i << " is " << dist.at( i ) << endl;
    }

    cout << "The paths (backwards) are:" << endl;
    for( uint i = 1; i < graph.size(); i++ )
    {
        cout << "v" << i << ": ";
        tracePath( i, path );
        cout << endl;
    }
}
