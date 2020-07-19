public class NBody {
  public static String imageToDraw = "images/starfield.jpg";

  public static double readRadius(String fileName) {
    In in = new In(fileName);
    int numPlanets = in.readInt();
    double radius = in.readDouble();
    return radius;
  }

  public static Body[] readBodies(String fileName) {
    In in = new In(fileName);
    int numPlanets = in.readInt();
    double radius = in.readDouble();
    Body[] bodyarray = new Body[numPlanets];
    for(int i = 0;i<bodyarray.length;i++) {
      bodyarray[i] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
    }
    return bodyarray;
  }

  public static void main(String[] args) {
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];
    double radius = readRadius(filename);
    Body[] bodyArr = readBodies(filename);
    StdDraw.setScale(-radius,radius);
    StdDraw.picture(0, 0, imageToDraw, 2*radius, 2*radius);
    for(int i = 0; i<bodyArr.length; i++) {
      bodyArr[i].draw();
    }
    StdDraw.enableDoubleBuffering();
    double time;
    for(time = 0;time <= T; time += dt) {
      double[] xForces = new double[bodyArr.length];
      double[] yForces = new double[bodyArr.length];
      for(int i=0;i<bodyArr.length;i++) {
        xForces[i] = bodyArr[i].calcNetForceExertedByX(bodyArr);
      }
      for(int i=0;i<bodyArr.length;i++) {
        yForces[i] = bodyArr[i].calcNetForceExertedByY(bodyArr);
      }
      for(int i=0;i<bodyArr.length;i++) {
        bodyArr[i].update(dt,xForces[i],yForces[i]);
      }
      StdDraw.picture(0, 0, imageToDraw, 2*radius, 2*radius);
      for(int i = 0; i<bodyArr.length; i++) {
        bodyArr[i].draw();
      }
      StdDraw.show();
      StdDraw.pause(10);
    }

    StdOut.printf("%d\n", bodyArr.length);
    StdOut.printf("%.2e\n", radius);
    for (int i = 0; i < bodyArr.length; i++) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
      bodyArr[i].xxPos, bodyArr[i].yyPos, bodyArr[i].xxVel,
      bodyArr[i].yyVel, bodyArr[i].mass, bodyArr[i].imgFileName);   
    }
  }
}
