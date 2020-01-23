import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class OCR {
    static class Point implements Comparable<Point> {
        private int x0;
        private int y0;
        private int x1;
        private int y1;
        private int x2;
        private int y2;
        private int x3;
        private int y3;
        private String text;
        public Point(List<String> line){
            String[] s = line.get(0).toString().split(",");
            this.x0 = Integer.parseInt(s[0]);
            this.y0 = Integer.parseInt(s[1]);
            s = line.get(1).toString().split(",");
            this.x1 = Integer.parseInt(s[0]);
            this.y1 = Integer.parseInt(s[1]);
            s = line.get(2).toString().split(",");
            this.x2 = Integer.parseInt(s[0]);
            this.y2 = Integer.parseInt(s[1]);
            s = line.get(3).toString().split(",");
            this.x3 = Integer.parseInt(s[0]);
            this.y3 = Integer.parseInt(s[1]);
            this.text = line.get(4).toString();
        }

        @Override
        public int compareTo(Point point) {
            return (this.y0 < point.y0)?1:0;
        }
        int xdistance(Point p) {
            int dx = this.x0 - p.x0;
            return (int) Math.sqrt(dx * dx);
        }
        int ydistance(Point p){
            int dy = this.y0 - p.y0;
            return (int) Math.sqrt(dy*dy);
        }
        int length(){
            int dx = this.x1 - this.x0;
            return dx;
        }

    }

    public static void main(String[] args){
        String csvFile = "ocr.csv";
        ArrayList<Point> points = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(csvFile));
            while (scanner.hasNext()) {
                List<String> line = CSVUtils.parseLine(scanner.nextLine());
                points.add(new Point(line));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Point old = points.get(0);
        String out="\""+old.text+"\" ";
        int totalx = 0;
        for(int i=1;i<points.size();i++){
            Point p = points.get(i);
            int xdistance = old.xdistance(p);
            int ydistance = old.ydistance(p);
            if(xdistance >= totalx  &&  ydistance<=3){
                out+="\""+p.text+"\" ";
                totalx += p.length();
            }else{
                System.out.println(out);
                out = "\"" + p.text + "\" ";
                old = p;
                totalx = 0;
            }
        }
    }

}
