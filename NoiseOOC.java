package noiseOOC;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NoiseOOC {
    public static void main(String[] args){
        double[][][] map = new double[129][129][129];
        map[0][0][0] = Math.random();
        map[128][128][0] = Math.random();
        map[0][128][0] = Math.random();
        map[128][0][0] = Math.random();
        map[0][0][128] = Math.random();
        map[128][128][128] = Math.random();
        map[0][128][128] = Math.random();
        map[128][0][128] = Math.random();
        new Window(OOS(map)).createWindow();
    }
    public static double[] midpointDisplacement(double[] map){
        int size = (int)(Math.log(map.length) / Math.log(2)) - 1;
        for(int mag = size; mag >= 0; mag--){
            int magpow = 1 << mag;
            for(int x = magpow; x < map.length - 1; x += magpow)
                if(map[x] == 0)
                    map[x] = (map[x - magpow] + map[x + magpow]) / 2 + (1.0 / (1 << (size - mag))) * Math.random() - (1.0 / (2 << (size - mag)));
        }
        return map;
    }
    public static double[][] diamondSquare(double[][] map){
        int size = (int)(Math.log(map.length) / Math.log(2)) - 1;
        for(int mag = size; mag >= 0; mag--){
            int magpow = 1 << mag;
            for(int x = magpow; x < map.length - 1; x += magpow)
                for(int y = magpow; y < map.length - 1; y += magpow){
                    if(map[x][y] == 0){
                        map[x][y] = map[x - magpow][y - magpow] + map[x + magpow][y - magpow] + map[x - magpow][y + magpow] + map[x + magpow][y + magpow];
                        map[x][y] = map[x][y] / 4 + (1.0 / (1 << (size - mag))) * Math.random() - (1.0 / (2 << (size - mag)));
                    }
                }
            boolean curr = false;
            for(int x = 0; x < map.length; x += magpow){
                for(int y = 0; y < map.length; y += magpow){
                    if(curr){
                        int cnt = 4;
                        map[x][y] = 0;
                        try { map[x][y] += map[x][y + magpow]; } catch (Exception e){ cnt--; }
                        try { map[x][y] += map[x][y - magpow]; } catch (Exception e){ cnt--; }
                        try { map[x][y] += map[x + magpow][y]; } catch (Exception e){ cnt--; }
                        try { map[x][y] += map[x - magpow][y]; } catch (Exception e){ cnt--; }
                        map[x][y] /= cnt;
                        map[x][y] += ((1.0 / (1 << (size - mag))) * Math.random() - (1.0 / (2 << (size - mag))));
                    } curr =! curr;
                }
            }
        }
        return map;
    }
    public static double[][][] OOC(double[][][] map){
        int size = (int)(Math.log(map.length) / Math.log(2)) - 1;
        for(int mag = size; mag >= 0; mag--){
            int magpow = 1 << mag;
            for(int x = 1 << mag; x < map.length - 1; x += magpow)
                for(int y = 1 << mag; y < map.length - 1; y += magpow)
                    for(int z = 1 << mag; z < map.length - 1; z += magpow){
                        if(map[x][y][z] == 0){
                            map[x][y][z] += map[x - magpow][y - magpow][z - magpow];
                            map[x][y][z] += map[x - magpow][y - magpow][z + magpow];
                            map[x][y][z] += map[x - magpow][y + magpow][z - magpow];
                            map[x][y][z] += map[x - magpow][y + magpow][z + magpow];
                            map[x][y][z] += map[x + magpow][y - magpow][z - magpow];
                            map[x][y][z] += map[x + magpow][y - magpow][z + magpow];
                            map[x][y][z] += map[x + magpow][y + magpow][z - magpow];
                            map[x][y][z] += map[x + magpow][y + magpow][z + magpow];
                            map[x][y][z] = map[x][y][z] / 8 + ((1.0 / (1 << (size - mag))) * Math.random() - (1.0 / (2 << (size - mag))));
                            if(x == 64 && y == 64 && z == 64) System.out.println(x + ", " + y + ", " + z + ": " + magpow + " = " + map[x][y][z]);
                           // map[x][y][z] = 1;
                        }
                    }
            for(int x = 0; x < map.length; x += 1 << mag)
                for(int y = 0; y < map.length; y += 1 << mag)
                    for(int z = 0; z < map.length; z += 1 << mag){
                        boolean xon = ((x / (2 << mag)) * (2 << mag)) == x;
                        boolean yon = ((y / (2 << mag)) * (2 << mag)) == y;
                        boolean zon = ((z / (2 << mag)) * (2 << mag)) == z;
                        if((xon ? 1 : 0) + (yon ? 1 : 0) + (zon ? 1 : 0) == 1){
                            String side = xon ? "x" : (yon ? "y" : (zon ? "z" : null));
                            map[x][y][z] = side(x, y, z, map, magpow, side) + ((1.0 / (1 << (size - mag))) * Math.random() - (1.0 / (2 << (size - mag))));
                        }
                    }
            for(int x = 0; x < map.length; x += 1 << mag)
                for(int y = 0; y < map.length; y += 1 << mag)
                    for(int z = 0; z < map.length; z += 1 << mag){
                        boolean xon = ((x / (2 << mag)) * (2 << mag)) == x;
                        boolean yon = ((y / (2 << mag)) * (2 << mag)) == y;
                        boolean zon = ((z / (2 << mag)) * (2 << mag)) == z;
                        if((xon ? 1 : 0) + (yon ? 1 : 0) + (zon ? 1 : 0) == 2)
                            map[x][y][z] = edge(x, y, z, map, magpow) + ((1.0 / (1 << (size - mag))) * Math.random() - (1.0 / (2 << (size - mag))));
                    }
        }
        return map;
    }
    public static double side(int x, int y, int z, double[][][] map, int mag, String side){
        int cnt = 6;
        if(side.equals("y")){
            double sum = map[x - mag][y][z - mag] + map[x + mag][y][z - mag] + map[x - mag][y][z + mag] + map[x + mag][y][z + mag];
            if(y - mag < 0) cnt--;
            else sum += map[x][y - mag][z];
            if(y + mag >= map.length) cnt--;
            else sum += map[x][y + mag][z];
            return sum / cnt;
        }
        else if(side.equals("x")){
            double sum = map[x][y - mag][z - mag] + map[x][y + mag][z - mag] + map[x][y - mag][z + mag] + map[x][y + mag][z + mag];
            if(x - mag < 0) cnt--;
            else sum += map[x - mag][y][z];
            if(x + mag >= map.length) cnt--;
            else sum += map[x + mag][y][z];
            return sum / cnt;
        }
        else if(side.equals("z")){
            double sum = map[x - mag][y - mag][z] + map[x - mag][y + mag][z] + map[x + mag][y - mag][z] + map[x + mag][y + mag][z];
            if(z - mag < 0) cnt--;
            else sum += map[x][y][z - mag];
            if(z + mag >= map.length) cnt--;
            else sum += map[x][y][z + mag];
            return sum / cnt;
        }
        else return 0;
    }
    public static double edge(int x, int y, int z, double[][][] map, int mag){
        int cnt = 6;
        double sum = 0;
        try { sum += map[x - mag][y][z]; } catch (Exception e) { cnt--; }
        try { sum += map[x + mag][y][z]; } catch (Exception e) { cnt--; }
        try { sum += map[x][y - mag][z]; } catch (Exception e) { cnt--; }
        try { sum += map[x][y + mag][z]; } catch (Exception e) { cnt--; }
        try { sum += map[x][y][z - mag]; } catch (Exception e) { cnt--; }
        try { sum += map[x][y][z + mag]; } catch (Exception e) { cnt--; }
        return sum / cnt;
    }
    public static void generate(double[][][][] map){}
}
class Window extends JPanel {
    JFrame frame = new JFrame("Terrain");
    int dimensions;
    double[] D1map;
    double[][] D2map;
    double[][][] D3map;
    double[][][][] D4map;
    public Window(double[] map){
        super();
        this.D1map = map;
        dimensions = 1;
    }
    public Window(double[][] map){
        super();
        this.D2map = map;
        dimensions = 2;
    }
    public Window(double[][][] map){
        super();
        this.D3map = map;
        dimensions = 3;
    }
    public void createWindow(){
        setPreferredSize(new Dimension(516, 516));
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);
        repaint();
    }
    @Override public void paintComponent(Graphics G){
        Graphics2D g = (Graphics2D) G;
        if(dimensions == 1){
            for(int x = 0; x < 128; x++){
                g.drawRect(x, (int)(128 * D1map[x]), 1, 128 - (int)(128 * D1map[x]));
            }
        }
        if(dimensions == 2){
            for(int x = 0; x < D2map.length; x++)
                for(int y = 0; y < D2map.length; y++){
                    if(D2map[x][y] > 0.9)
                        g.setColor(Color.white);
                    else if(D2map[x][y] > 0.75)
                        g.setColor(new Color(0xCCCCCC));
                    else if(D2map[x][y] > 0.6)
                        g.setColor(new Color(0x88FF88));
                    else if(D2map[x][y] > 0.5)
                        g.setColor(Color.green);
                    else if(D2map[x][y] > 0.4)
                        g.setColor(new Color(0x009900));
                    else if(D2map[x][y] > 0.25)
                        g.setColor(new Color(0x8888FF));
                    else g.setColor(Color.blue);
                    g.fillRect(x, y, 1, 1);
                }
        }
        if(dimensions == 3){
            for(int x = 0; x < D3map.length; x++)
                for(int y = 0; y < D3map.length; y++){
                    int col = 0;
                    if(D3map[x][y][col] > 0.9)
                        g.setColor(Color.white);
                    else if(D3map[x][y][col] > 0.75)
                        g.setColor(new Color(0xCCCCCC));
                    else if(D3map[x][y][col] > 0.6)
                        g.setColor(new Color(0x88FF88));
                    else if(D3map[x][y][col] > 0.5)
                        g.setColor(Color.green);
                    else if(D3map[x][y][col] > 0.4)
                        g.setColor(new Color(0x009900));
                    else if(D3map[x][y][col] > 0.25)
                        g.setColor(new Color(0x8888FF));
                    else g.setColor(Color.blue);
                    g.fillRect(x * 4, y * 4, 4, 4);
                }
        }
    }
}
