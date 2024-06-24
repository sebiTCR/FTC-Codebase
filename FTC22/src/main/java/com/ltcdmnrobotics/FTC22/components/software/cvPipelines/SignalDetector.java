package com.ltcdmnrobotics.FTC22.components.software.cvPipelines;


import static org.opencv.core.Core.max;
import static org.opencv.core.Core.mean;

import com.sun.tools.javac.util.List;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class SignalDetector extends OpenCvPipeline {


    enum Colors{
        RED,
        GREEN,
        BLUE
    }
    enum Bound{
        LOW,
        HIGH
    }

    private  Telemetry telemetry;
    private int ColorSensivity = 50;
    private Scalar ColorBounds[][]={
        { new Scalar(255 - ColorSensivity, 0, 0), new Scalar(255, ColorSensivity, ColorSensivity) },
        { new Scalar(0, 255 - ColorSensivity, 0), new Scalar(ColorSensivity, 255, ColorSensivity) },
        { new Scalar(0, 0, 255 - ColorSensivity), new Scalar(ColorSensivity, ColorSensivity, 255 ) },

    };

    public SignalDetector(Telemetry telemetry){
        this.telemetry = telemetry;
    }

    @Override
    public Mat processFrame(Mat input) {
        Mat detectionArea = input;
        int size = 15;
        double offset = 125;

        Point pt1 = new Point((offset + detectionArea.rows() / 4) - size - 150 , (offset + detectionArea.cols() / 4) - size);
        Point pt2 = new Point((offset + detectionArea.rows() / 4) + size - 150, (offset + detectionArea.cols() / 4) + size);
        Rect detRect = new Rect(pt1, pt2);

        Imgproc.rectangle(detectionArea, detRect, new Scalar(255,255,0));
//        Mat croppedArea = detectionArea.submat(detRect);
        Mat croppedArea = detectionArea;
        Imgproc.cvtColor(croppedArea, croppedArea, Imgproc.COLOR_RGB2YCrCb);

        List<Mat> SplitClr = null;
        Core.split(croppedArea, SplitClr);
//        Imgproc.cvtColor(croppedArea, croppedArea, Imgproc.COLOR_BGR2HSV);


        Mat INR = input;
        int bound = Bound.LOW.ordinal();
        int color = Colors.RED.ordinal();

//        Core.inRange(croppedArea, ColorBounds[color][Bound.HIGH.ordinal()], ColorBounds[color][bound] , INR);

        double hiCo = 0;
        int colIDX = 0;

        Mat ExtractedColors[] = {new Mat(), new Mat(), new Mat()};

//        for(int i = 0; i < 2; i++) { Core.extractChannel(croppedArea, ExtractedColors[i], i); }
//
//
//
//        for(int i = 0; i <= 2; i++) {
//            Scalar clr = mean(ExtractedColors[i]);
//            if(hiCo < clr.val[0]){
//                hiCo = clr.val[0];
//                colIDX++;
//            }
//            telemetry.addData("Color HiCo:", colIDX);
//            telemetry.update();
//
//        }
        Scalar clr = mean(input);

        for(int i = 0; i < 2; i++) {
            Mat extr = new Mat();
            Core.extractChannel(croppedArea, extr, i);
//            Scalar clr = mean(detectionArea);


            if(hiCo < clr.val[0]){
                hiCo = clr.val[0];
                colIDX++;
            }
        }

        telemetry.addData("CLR", clr);
        telemetry.addData("HiCo", colIDX);
        telemetry.update();

        return detectionArea;
    }
}


// Aerostar Bacau
//
