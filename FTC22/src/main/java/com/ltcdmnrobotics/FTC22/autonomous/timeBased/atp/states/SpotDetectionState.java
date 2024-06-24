package com.ltcdmnrobotics.FTC22.autonomous.timeBased.atp.states;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ltcdmnrobotics.FTC22.components.external.apriltags.AprilTagDetectionPipeline;
//import com.ltcdmnrobotics.FTC22.components.hardware.Robot;
//import com.ltcdmnrobotics.FTC22.components.software.fsm.states.State;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
//
//public class SpotDetectionState extends State {
//    int cameraMonitorViewId = 0;
//
//    //Camera Calibration Params
//    double fx = 578.272;
//    double fy = 578.272;
//    double cx = 402.145;
//    double cy = 221.506;
//
//    AprilTagDetection detectedTag = null;
//    OpenCvInternalCamera camera;
//    AprilTagDetectionPipeline apriltagPipeline;
//
//    public SpotDetectionState(@NonNull HardwareMap hardwareMap) {
//        super(hardwareMap);
//    }
//
//
//    @Override
//    public void enter(@Nullable Robot parent) {
//        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        apriltagPipeline = new AprilTagDetectionPipeline(0.166, fx, fy,cx, cy );
//        camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
//
//
//        camera.setPipeline(apriltagPipeline);
//        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener(){
//            @Override
//            public void onOpened() {
//                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
//                camera.setZoom(3);
//                camera.setFlashlightEnabled(true);
//            }
//
//            @Override
//            public void onError(int errorCode) {
//
//            }
//        });
//    }
//
//    @Override
//    public void update(@Nullable Robot parent) {
//
//    }
//
//    @Override
//    public void exit(@Nullable Robot parent) {
//        if(apriltagPipeline.getLatestDetections().size() == 0){
//            parent.parkingSpot = apriltagPipeline.getLatestDetections().get(0).id;
//
//        }
//    }
//}
