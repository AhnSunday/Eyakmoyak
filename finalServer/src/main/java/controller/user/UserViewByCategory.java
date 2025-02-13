package controller.user;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import persistence.MyBatisConnectionFactory;
import persistence.dao.ManagerDAO;
import persistence.dao.MedicineDAO;
import persistence.dto.RefineData;
import persistence.dto.UserDTO;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserViewByCategory implements Initializable {
    @FXML private ListView<String> medicineListField;
    private ObservableList<String> medicineList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        medicineList = FXCollections.observableArrayList();
        medicineListField.setItems(medicineList);
    }

    public void inquiryElderlyCautionMedicine(ActionEvent event) {
        try {
            MedicineDAO medicineDAO = new MedicineDAO(MyBatisConnectionFactory.getSqlSessionFactory());
            List<RefineData> resultList = medicineDAO.inquiryElderlyCautionMedicine();

            medicineList.clear();

            for (RefineData data : resultList) {
                String list = "품목명: |" + data.getItemName() + "|\n"
                        + "※주의해야하는 사람※\n" + splitString(data.getCautionPeople())
                        + "효능효과: " + splitString(data.getEfficacy())
                        + "사용법: " + splitString(data.getUseMethod())
                        + "주의사항: " + splitString(data.getCaution())
                        + "상호작용: " + splitString(data.getIntrc())
                        + "부작용: " + splitString(data.getSideEffect()) + "\n"
                        + "--------------------------------------------------------------------------------------------";
                list = list.replace("null", "");
                medicineList.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inquiryPregnantWomanCautionMedicine(ActionEvent event) {
        try {
            MedicineDAO medicineDAO = new MedicineDAO(MyBatisConnectionFactory.getSqlSessionFactory());
            List<RefineData> resultList = medicineDAO.inquiryPregnantWomanCautionMedicine();

            medicineList.clear();

            for (RefineData data : resultList) {
                String list = "품목명: |" + data.getItemName() + "|\n"
                        + "※주의해야하는 사람※\n" + splitString(data.getCautionPeople())
                        + "효능효과: " + splitString(data.getEfficacy())
                        + "사용법: " + splitString(data.getUseMethod())
                        + "주의사항: " + splitString(data.getCaution())
                        + "상호작용: " + splitString(data.getIntrc())
                        + "부작용: " + splitString(data.getSideEffect()) + "\n"
                        + "--------------------------------------------------------------------------------------------";
                list = list.replace("null", "");
                medicineList.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inquiryChildCautionMedicine(ActionEvent event) {
        try {
            MedicineDAO medicineDAO = new MedicineDAO(MyBatisConnectionFactory.getSqlSessionFactory());
            List<RefineData> resultList = medicineDAO.inquiryChildCautionMedicine();

            medicineList.clear();

            for (RefineData data : resultList) {
                String list = "품목명: |" + data.getItemName() + "|\n"
                        + "※주의해야하는 사람※\n" + splitString(data.getCautionPeople())
                        + "효능효과: " + splitString(data.getEfficacy())
                        + "사용법: " + splitString(data.getUseMethod())
                        + "주의사항: " + splitString(data.getCaution())
                        + "상호작용: " + splitString(data.getIntrc())
                        + "부작용: " + splitString(data.getSideEffect()) + "\n"
                        + "--------------------------------------------------------------------------------------------";
                list = list.replace("null", "");
                medicineList.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showImage() throws IOException {
        String selectedAdmin = medicineListField.getSelectionModel().getSelectedItem();
        String arr[] = selectedAdmin.split("\\|");
        String name = arr[1];

        MedicineDAO medicineDAO = new MedicineDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        String ImgUrl = null;
        ImgUrl = medicineDAO.inquiryMedicineImageByName(name);

        // Alert 다이얼로그 생성
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if(ImgUrl != null) {
            ImageView imageView = new ImageView();
            imageView.setPreserveRatio(true); // 원본 비율 유지 (Aspect Ratio)

            // 원본 이미지 크기 가져오기
            URL imageUrl = new URL(ImgUrl);
            Image originalImage = new Image(imageUrl.openStream());
            int originalWidth = (int) originalImage.getWidth();
            int originalHeight = (int) originalImage.getHeight();

            // 원하는 크기 계산
            int targetWidth = 500; // 원하는 폭
            int targetHeight = 500 * originalHeight / originalWidth; // Aspect Ratio에 따라 높이 계산

            // 이미지 생성
            Image resizedImage = new Image(imageUrl.openStream(), targetWidth, targetHeight, false, true); // 오류 발생 시, 예외 처리 필요

            // ImageView에 이미지 설정
            imageView.setImage(resizedImage);

            // 이미지 보기
            imageView.setImage(resizedImage);
            alert.setGraphic(imageView);
        }

        // Alert 다이얼로그 표시
        alert.showAndWait();

    }

    public String splitString(String content) {
        StringBuilder result = new StringBuilder();
        int startIndex = 0;
        int endIndex = 40;

        while (startIndex < content.length()) {
            if (endIndex > content.length()) {
                endIndex = content.length();
            }

            String subString = content.substring(startIndex, endIndex);
            result.append(subString).append("\n");

            startIndex += 40;
            endIndex += 40;
        }

        return result.toString();
    }

    public void goToPreviousScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/JavaFX/user/userMainUI.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
