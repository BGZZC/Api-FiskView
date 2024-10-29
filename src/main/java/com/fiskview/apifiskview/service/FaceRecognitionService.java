package com.fiskview.apifiskview.service;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

@Service
public class FaceRecognitionService {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private CascadeClassifier faceDetector;
    private String storedImagePath; // Ruta de la imagen de referencia

    public FaceRecognitionService() {
        faceDetector = new CascadeClassifier("haarcascade_frontalface_default.xml");
        // Carga tu modelo de comparación aquí si es necesario
    }

    public void startRecognition() {
        VideoCapture camera = new VideoCapture(0); // Inicializa la cámara
        Mat frame = new Mat();

        if (!camera.isOpened()) {
            System.out.println("Error al abrir la cámara.");
            return;
        }

        while (true) {
            camera.read(frame);
            MatOfRect faceDetections = new MatOfRect();
            faceDetector.detectMultiScale(frame, faceDetections);

            // Procesa cada detección de rostro
            for (Rect rect : faceDetections.toArray()) {
                Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
                // Aquí puedes agregar la lógica para comparar con la imagen de referencia
                if (compareFaces(frame.submat(rect))) {
                    System.out.println("Rostro reconocido.");
                }
            }

            // Mostrar el frame (opcional)
            HighGui.imshow("Reconocimiento Facial", frame);
            if (HighGui.waitKey(30) >= 0) break; // Salir si se presiona cualquier tecla
        }
        camera.release();
    }

    private boolean compareFaces(Mat detectedFace) {
        // Lógica para comparar el rostro detectado con la imagen almacenada
        // Aquí puedes implementar el algoritmo que elijas (por ejemplo, Eigenfaces, Fisherfaces, etc.)
        return false; // Cambia esto según la lógica de comparación
    }
}
