package com.ltcdmnrobotics.FTC22.components.software.cvPipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class ConeAlignment extends OpenCvPipeline {
    Telemetry telemetry;

    public ConeAlignment(Telemetry telemetry){
        this.telemetry = telemetry;
    }

    @Override
    public Mat processFrame(Mat input) {

        Mat colorDecompose = input;
        Mat topology = new Mat(input.rows(), input.cols(), input.type(), new Scalar(0));
        Mat compose = new Mat(input.rows(), input.cols(), input.type(), new Scalar(0));
        List<MatOfPoint> contours = new ArrayList<>();

        Imgproc.cvtColor(input, colorDecompose, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(colorDecompose, colorDecompose, new Size(15,15), 1);
        Imgproc.Canny(colorDecompose, input, 160, 200);
        Imgproc.findContours(colorDecompose, contours, topology, 0, 0);
	
//        telemetry.addData("Contours", contours);
//        telemetry.update();

         compose = colorDecompose;
//
//        for(int i  = 0; i < contours.size(); i++){
//            Imgproc.rectangle(compose, new Rect(c[i], ctr[i+1]), new Scalar(255, 255, 0, 255));
//        }


        return compose;
    }
}
