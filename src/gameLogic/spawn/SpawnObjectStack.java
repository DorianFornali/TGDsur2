package gameLogic.spawn;

import java.util.Stack;

/** Very basic Stack class using built-in java stack containing SpawnObjects to facilitate the JSON -> Java object */
public class SpawnObjectStack {
    private Stack<SpawnObject> stack;

    public SpawnObjectStack(){
        this.stack = new Stack<SpawnObject>();
    }

    public SpawnObject pop(){
        return stack.pop();
    }

    public void push(SpawnObject so){
        stack.push(so);
    }

    public boolean isEmpty(){
        return stack.empty();
    }
    public int length(){
        return stack.size();
    }

    public void print(){
        for(SpawnObject s: stack){
            System.out.println("{" + s.type + ", " + s.row + ", " + s.delay + "}");
        }
    }
}
