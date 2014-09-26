package commentedOuts;

public class test {
    
    public static void main(String args[]){
        String[] ss=new String[]{"1-2","1--2","-1-2","-1--2"};
        for(String s:ss){
            String[] ekchin=s.split("-");
            for(String k:ekchin)
                System.out.println(k);
            System.out.println("Next");
        }
    }
    
}
