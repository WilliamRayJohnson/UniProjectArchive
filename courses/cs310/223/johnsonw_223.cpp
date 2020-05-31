/**
 * A simple framework to implement a first-child, right-sibling tree with
 * unlimited children per node.
 * @author Original Author Jon Beck
 * @author Edited Author William Ray Johnson
 * @version 2016 February 15
 */

#include <iostream>
#include <queue>
using namespace std;

/**
 * A simple tree_node class
 */
class tree_node
{
    public:
        tree_node( char data, tree_node * first_child, tree_node * right_sibling )
            : data( data ), first_child( first_child ), right_sibling( right_sibling )
        {}

        char get_data() const
        {
            return data;
        }

        tree_node * get_first_child() const
        {
            return first_child;
        }

        tree_node * get_right_sibling() const
        {
            return right_sibling;
        }

    private:
        char data;
        tree_node * first_child;
        tree_node * right_sibling;
};

void visit( tree_node * node );
void preorder( tree_node * node );
void levelOrder( tree_node * node );

int main()
{
    tree_node * q = new tree_node{ 'Q', nullptr, nullptr };
    tree_node * p = new tree_node{ 'P', nullptr, q };
    tree_node * o = new tree_node{ 'O', nullptr, p };
    tree_node * n = new tree_node{ 'N', nullptr, nullptr };
    tree_node * m = new tree_node{ 'M', nullptr, n };
    tree_node * l = new tree_node{ 'L', nullptr, nullptr };
    tree_node * k = new tree_node{ 'K', nullptr, l };
    tree_node * j = new tree_node{ 'J', nullptr, k };
    tree_node * i = new tree_node{ 'I', o, nullptr };
    tree_node * h = new tree_node{ 'H', nullptr, i };
    tree_node * g = new tree_node{ 'G', nullptr, h };
    tree_node * f = new tree_node{ 'F', m, nullptr };
    tree_node * e = new tree_node{ 'E', j, nullptr };
    tree_node * d = new tree_node{ 'D', g, e };
    tree_node * c = new tree_node{ 'C', nullptr, d };
    tree_node * b = new tree_node{ 'B', f, c };
    tree_node * a = new tree_node{ 'A', b, nullptr };

    cout << "Preorder: ";
    preorder( a );
    cout << endl;
    cout << "Level Order: ";
    levelOrder( a );
    cout << endl;

    delete a;
    delete b;
    delete c;
    delete d;
    delete e;
    delete f;
    delete g;
    delete h;
    delete i;
    delete j;
    delete k;
    delete l;
    delete m;
    delete n;
    delete o;
    delete p;
    delete q;

    return 0;
}

/**
 * Will visit the node by printing out its data.
 * @param node the given node
 */
void visit( tree_node * node )
{
    cout << node->get_data() << ' ';
}

/**
 * Preforms a preorder traversal.
 * @param node the root of a tree
 */
void preorder( tree_node * node )
{
    if( node != nullptr )
    {
        visit( node );
        preorder( node->get_first_child() );
        preorder( node->get_right_sibling() );
    }
}

/**
 * Preforms a level order traversal.
 * @param node the root of a tree.
 */
void levelOrder( tree_node * node )
{
    if(node != nullptr )
    {
        tree_node * currentNode = node;
        queue< tree_node * > firstChildren;

        visit( currentNode );
        if( currentNode->get_first_child() != nullptr )
        {
            firstChildren.push( currentNode->get_first_child() );
            currentNode = currentNode->get_first_child();
        } 
        while( !firstChildren.empty() )
        {
            while( currentNode != nullptr )
            {
                visit( currentNode );
                if( currentNode->get_first_child() != nullptr )
                    firstChildren.push( currentNode->get_first_child() );

                currentNode = currentNode->get_right_sibling();
            }
        
            firstChildren.pop();
            currentNode = firstChildren.front();
        }
    }
}
