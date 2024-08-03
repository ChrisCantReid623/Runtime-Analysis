public class RBT {
	private Node root;
	private int size;

	/* CONSTRUCTOR */
	public RBT() {
		this.root = null;
		this.size = 0;
	}

	/* PUBLIC METHODS */

	/***
	 * insert a new key into tree or update the count if the key already exists
	 */
	public void put(int key) {
		if (root == null) {
			root = new Node(key);
			size++;
		} else
			root = put(key, root);
		root.setColor(Node.BLACK);
	}

	/***
	 * get the count associated with the given key; return 0 if key doesn't exist in
	 * the tree
	 */
	public int get(int key) {
		Node x = root;
		while (x != null) {
			if (key == x.key())
				return x.count();
			if (key > x.key())
				x = x.right();
			else
				x = x.left();
		}
		return 0;
	}

	/***
	 * get the color of a node
	 ***/
	public String getColor(int key) {
		Node x = get(key, root);
		if (x == null)
			return null;
		if (x.isRed())
			return "RED";
		return "BLACK";
	}

	/***
	 * return true if the tree is empty and false otherwise
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/***
	 * return the number of Nodes in the tree
	 */
	public int size() {
		return size;
	}

	/***
	 * returns the height of the tree
	 */
	public int height() {
		return height(root);
	}

	/***
	 * returns the height of node with given key in the tree; return -1 if the key
	 * does not exist in the tree
	 */
	public int height(int key) {
		Node x = get(key, root);
		return height(x);
	}

	/***
	 * returns true if the key is in the tree and false otherwise
	 ***/
	public boolean contains(int key) {
		Node x = get(key, root);
		if (x != null)
			return true;
		return false;
	}

	/***
	 * return the depth of the node with the given key in the tree; return -1 if the
	 * key does not exist in the tree
	 ***/
	public int depth(int key) {
		Node cur = root;
		int depth = 0;
		
		while (cur != null) {
			int cmp = Integer.compare(key, cur.key());
			if (cmp == 0) {
				return depth;
			} else if (cmp < 0) {
				cur = cur.left();
			} else {
				cur = cur.right();
			}
			depth++;
		}
		return -1;
	}

	/***
	 * return the size of the subtree rooted at the given key
	 ***/
	public int size(int key) {
		Node node = get(key, root);
		if (node == null) {
			return 0;
		}
		return node.N();
	}
	
	
	/***
	 * return the minimum key
	 ***/
	public int min() throws EmptyTreeException {
		if (root == null) {
			throw new EmptyTreeException();
		}
		
		Node cur = root;
		while (cur.left() != null) {
			cur = cur.left();
		}
		return cur.key();
	}

	/***
	 * return the maximum key
	 ***/
	public int max() throws EmptyTreeException {
		if (root == null) {
			throw new EmptyTreeException();
		}
		
		Node cur = root;
		while (cur.right() != null) {
			cur = cur.right();
		}
		return cur.key();
	}

	/***
	 * return the largest key that is less than or equal to the parameter
	 ***/
	public int floor(int key) throws KeyDoesNotExistException {
	    Node result = floor(root, key);
	    if (result == null) {
	        throw new KeyDoesNotExistException();
	    }
	    return result.key();
	}
	
	private Node floor(Node x, int key) {
	    if (x == null) {
	        return null;  // Base case: reached bottom of tree without finding floor.
	    }
	    int cmp = Integer.compare(key, x.key());
	    if (cmp == 0) {
	        return x;  // Found the key, which is the floor itself.
	    } else if (cmp < 0) {
	        return floor(x.left(), key);  // Floor must be in the left subtree.
	    } else {
	        Node t = floor(x.right(), key);  // Potential floor might be in the right subtree.
	        return (t != null) ? t : x;  // If there is no floor in right subtree, current node is the floor.
	    }
	}

	/***
	 * return the smallest key that is greater than or equal to the parameter
	 ***/
	public int ceil(int key) throws KeyDoesNotExistException {
	    Node result = ceil(root, key);
	    if (result == null) {
	        throw new KeyDoesNotExistException();
	    }
	    return result.key();
	}

	private Node ceil(Node x, int key) {
	    if (x == null) {
	        return null;  // Base case: reached bottom of tree without finding a ceiling.
	    }
	    int cmp = Integer.compare(key, x.key());
	    if (cmp == 0) {
	        return x;  // Found the key, which is the ceiling itself.
	    } else if (cmp > 0) {
	        return ceil(x.right(), key);  // Ceiling must be in the right subtree.
	    } else {
	        Node t = ceil(x.left(), key);  // Potential ceiling might be in the left subtree.
	        return (t != null) ? t : x;  // If there is no ceiling in left subtree, current node is the ceiling.
	    }
	}

	/***
	 * return the number of keys that are less than the parameter
	 ***/
	public int rank(int key) {
		return rank(key, root);
	}
	
	private int rank(int key, Node x) {
	    if (x == null) return 0;
	    int cmp = Integer.compare(key, x.key());
	    if (cmp < 0) {
	        return rank(key, x.left());
	    } else if (cmp > 0) {
	        return 1 + size(x.left()) + rank(key, x.right());
	    } else {
	        return size(x.left());
	    }
	}
	

	/***
	 * return the key at the given rank
	 ***/
	public int select(int rank) throws NoSuchRankException {
		if (rank < 0 || rank >= size) {
			throw new NoSuchRankException();
		}
		Node node = select(root, rank);
		return node.key();
	}
	
	private Node select(Node x, int rank) {
		if (x == null) {
			return null;
		}
		int leftSize = (x.left() != null) ? x.left().N() : 0;
		
		if (rank < leftSize) {
			return select(x.left(), rank);
		} else if (rank > leftSize) {
			return select(x.right(), rank - leftSize - 1);
		} else {
			return x;
		}
	}

	/***
	 * return the number of keys in [lo...hi]
	 ***/
	public int size(int lo, int hi) {
	    if (lo > hi) {
	        return 0; // If lo is greater than hi, the range is invalid.
	    }

	    int rankLo = rank(lo - 1); // Get the rank of elements less than lo.
	    int rankHi = rank(hi); // Get the rank of elements less than or equal to hi.

	    if (contains(hi)) {
	        if (rankHi == -1 || rankLo == -1) return 0;
	        return rankHi - rankLo;
	    } else {
	        // hi is not in the tree, adjust rankHi to reflect the actual elements.
	        if (rankHi == -1 || rankLo == -1) return 0;
	        return rankHi - rankLo - 1;
	    }
	}
	

	/***
	 * return a String representation of the tree level by level
	 ***/
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Queue<Node> queue = new Queue<Node>();
		queue.enqueue(root);
		
		while (!queue.isEmpty()) {
			Node current = null; 
			try {
	            current = queue.dequeue();  // Attempt to dequeue the front node.
	        } catch (EmptyQueueException e) {
	            // This block should theoretically never be executed because we check isEmpty() in the loop condition.
	            continue;  // Skip the rest of the loop iteration if an error occurs.
	        }
			sb.append(current.toString());
			sb.append(", ");
			
			if (current.left() != null) {
				queue.enqueue(current.left());
			}
			if (current.right() != null) {
				queue.enqueue(current.right());
			}
		}
		if (sb.length() > 0) {
	        sb.setLength(sb.length() - 2);
	    }
	    return sb.toString();
	}

	/* PRIVATE METHODS */

	/***
	 * return the height of x or -1 if x is null
	 */
	private int height(Node x) {
		if (x == null)
			return -1;
		return x.height();
	}

	/***
	 * recursive helper method for insert
	 */
	private Node put(int key, Node x) {
		if (x == null) {
			Node newNode = new Node(key);
			newNode.setHeight(0);
			newNode.setN(1);
			size++;
			return newNode;
		}
		
		int cmp = Integer.compare(key, x.key());
		if (cmp < 0) {
			x.setLeft(put(key, x.left()));
		} else if (cmp > 0) {
			x.setRight(put(key, x.right()));
		} else {
			x.incCount();
		}
		return balanceAndUpdate(x);
	}
	
	private Node balanceAndUpdate(Node x) {
		x = balance(x);
		updateHeightAndSize(x);
		return x;
	}
	
	private void updateHeightAndSize(Node x) {
		x.setN(1 + size(x.left()) + size(x.right()));
		x.setHeight(1 + Math.max(height(x.left()), height(x.right())));
	}
	
	private int size(Node x) {
	    return (x == null) ? 0 : x.N();
	}
	
	/***
	 * recursive method for getting Node with given key
	 */
	private Node get(int key, Node x) {
		if (x == null)
			return null;
		if (key == x.key())
			return x;
		else if (key > x.key())
			return get(key, x.right());
		return get(key, x.left());
	}

	/***
	 * rotate left
	 ***/
	private Node rotateLeft(Node h) {
		Node x = h.right();
		h.setRight(x.left());
		x.setLeft(h);
		x.setColor(h.color());
		h.setColor(Node.RED);
		
		updateHeightAndSize(h);
	    updateHeightAndSize(x);
		return x;
	}

	/***
	 * rotate right
	 ***/
	private Node rotateRight(Node h) {
		Node x = h.left();
		h.setLeft(x.right());
		x.setRight(h);
		x.setColor(h.color());
		h.setColor(Node.RED);
		
		updateHeightAndSize(h);
	    updateHeightAndSize(x);
		return x;
	}

	/***
	 * color flip
	 ***/
	private void colorFlip(Node h) {
		h.setColor(Node.RED);
		h.left().setColor(Node.BLACK);
		h.right().setColor(Node.BLACK);
		if (h == root)
			h.setColor(Node.BLACK);
	}

	/***
	 * balance
	 ***/
	private Node balance(Node h) {
		if (h.right() != null && h.right().isRed() && h.right().right() != null && h.right().right().isRed()) {
			h = rotateLeft(h);
			colorFlip(h);
		} else if (h.left() != null && h.right() != null && h.left().isRed() && h.right().isRed())
			colorFlip(h);
		else if (h.left() != null && h.left().isRed())
			h = rotateRight(h);
		return h;
	}
}
