package com.ltcdmnrobotics.FTC22.components.software.cvPipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.Dictionary;
import org.opencv.objdetect.QRCodeDetector;

public class ArucoDetection extends OpenCvPipeline {
    QRCodeDetector decoder = new QRCodeDetector();
    Mat points = new Mat();
    Mat code;
    Telemetry tl;
    public String data = "None";

    Scalar color = new Scalar(255,255,255);

    public String getDetectionString(){
        return data;
    }

    public ArucoDetection(Telemetry telemetry){
        tl = telemetry;
    }

    Mat coderect = new Mat();

    @Override
    public Mat processFrame(Mat input) {
//        String data = decoder.detectAndDecode(input, points);
        decoder.detect(input, points);


        if(!points.empty()){

            for(int i = 0; i < points.cols(); i++){
                Point pt1 = new Point(points.get(0, i));
                Point pt2 = new Point(points.get(0, (i+1)%4));



                coderect = input.submat(new Rect(pt1, pt2));

                if(data == "1"){
                    color = new Scalar(255,0,0);
                }
                else if(data == "2"){
                    color = new Scalar(0, 255, 0);
                }
                else if(data == "3"){
                    color = new Scalar(0, 0, 255);
                }
                else{
                    color = new Scalar(0,0,0);
                }
                Imgproc.line(input, pt1, pt2, color, 3);


            }
        }

//        String data = decoder.detectAndDecode(input, points);



        tl.addData("Detected stuff: ", data);
        tl.update();
        return coderect;
    }
}
