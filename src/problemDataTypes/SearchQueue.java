package problemDataTypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

import tools.UninformedComparator;

public class SearchQueue {
	private Collection<Node> q;
	private String algorithm;
	private int depth;
	private Node initial;
	
	public SearchQueue(String algorithm, Node initial, int depth) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this(algorithm, initial);
		this.depth = depth;
	}
	public SearchQueue(String algorithm, Node initial) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.algorithm = algorithm;
		
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
			this.initPriorityQueue();
			break;
		default:
			throw new UnsupportedOperationException();
		}
		
		this.insert(new Node[]{initial});
	}
	
	private void initPriorityQueue(){
		switch(this.algorithm){
		case "UniformCost":
			this.q = new PriorityQueue<Node>(new UninformedComparator());
			break;
		case "APlus":
		case "Greedy":
			// TODO initialize Priority Queue with heuristics
		}
	}

	public void insert(Node[] nodes) throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException{
		Method insertMethod = this.getClass().getMethod("insert"+algorithm, nodes.getClass());
		insertMethod.invoke(this, nodes);
	}
	
	private void insertDFS(Node[] nodes) {
		for(Node node: nodes) {
			this.insertDFS(node);
		}
	}
	private void insertDFS(Node node) {
		((Stack<Node>) this.q).push(node);
	}
	
	@SuppressWarnings("unused")
	private void insertBFS(Node[] nodes) {
		for(Node node: nodes) {
			((LinkedList<Node>)this.q).add(node);		
		}
	}
	
	private void insertDepthLimited(Node[] nodes) {
		if(nodes[0].getDepth() <= depth) {
			insertDFS(nodes);
		}
	}
	
	@SuppressWarnings("unused")
	private void insertIterativeDeepening(Node[] nodes) throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, IllegalArgumentException, 
	InvocationTargetException {
		insertDepthLimited(nodes);
		if(this.q.isEmpty()) {
			this.depth++;
			this.insert(new Node[]{initial});
		}
	}
	
	
	public Node removeFront() throws IllegalAccessException, 
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException, 
	SecurityException{
		Method method = this.getClass().getMethod("remove"+this.algorithm);
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

}
