package problemDataTypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

import tools.SearchComparator;

public class SearchQueue {
	private Collection<Node> q;
	private String algorithm;
	private int depth;
	private Node initial;
	
	public SearchQueue(String algorithm, Node initial, int depth) throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException {
		this(algorithm, initial);
		this.depth = depth;
	}
	public SearchQueue(String algorithm, Node initial) throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException {
		this.algorithm = algorithm;
		this.initial = initial;
		
		switch(this.algorithm) {
		case "DepthLimited":
		case "IterativeDeepening":
		case "DFS":
			this.q = new Stack<Node>();
			break;
		case "BFS":
			this.q = new LinkedList<Node>();
			break;
		case "UniformCost":
		case "APlus":
		case "Greedy":
			this.q = new PriorityQueue<Node>(new SearchComparator());
			break;
		default:
			throw new UnsupportedOperationException();
		}
		ArrayList<Node> firstNode = new ArrayList<Node>();
		firstNode.add(initial);
		this.insert(firstNode);
	}

	public void insert(ArrayList<Node> nodes) throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException{
		Method insertMethod = this.getClass().getDeclaredMethod("insert"+algorithm, nodes.getClass());
		insertMethod.invoke(this, nodes);
	}
	
	private void insertDFS(ArrayList<Node> nodes) {
		for(Node node: nodes) {
			((Stack<Node>) this.q).push(node);
		}
	}

	
	@SuppressWarnings("unused")
	private void insertBFS(ArrayList<Node> nodes) {
		for(Node node: nodes) {
			((LinkedList<Node>)this.q).add(node);		
		}
	}
	
	private void insertDepthLimited(ArrayList<Node> nodes) {
		if(nodes.get(0).getDepth() <= depth) {
			insertDFS(nodes);
		}
	}
	
	@SuppressWarnings("unused")
	private void insertIterativeDeepening(ArrayList<Node> nodes) throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException {
		insertDepthLimited(nodes);
		if(this.q.isEmpty()) {
			this.depth++;
			ArrayList<Node> again = new ArrayList<Node>();
			again.add(initial);
			this.insert(again);
		}
	}
	
	
	public Node removeFront() throws IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException, 
	SecurityException{
		Method method = this.getClass().getDeclaredMethod("remove"+this.algorithm);
		return (Node) method.invoke(this);
	}
	
	private Node removeDFS(){
		return ((Stack<Node>)this.q).pop();
	}
	
	@SuppressWarnings("unused")
	private Node removeBFS(){
		return ((LinkedList<Node>)this.q).removeFirst();
	}
	
	private Node removeUniformCost(){
		return ((PriorityQueue<Node>)this.q).poll();
	}
	
	@SuppressWarnings("unused")
	private Node removeIterativeDeepening(){
		return this.removeDFS();
	}
	
	@SuppressWarnings("unused")
	private Node removeDepthLimited(){
		return this.removeDFS();
	}
	
	@SuppressWarnings("unused")
	private Node removeAPlus(){
		return this.removeUniformCost();
	}
	
	@SuppressWarnings("unused")
	private Node removeGreedy(){
		return this.removeUniformCost();
	}
	
	public boolean isEmpty() {
		return this.q.isEmpty();
	}

}
