<?php
    $connect = new mysqli("localhost", "root", "12345678");
    if($connect->connect_error){
        die($connect->connect_error);
    }
    $connect->set_charset("utf8");

    $sql = "USE news";
    $connect->query($sql);
    // echo '<script>alert("Kết nối thành công")</script>';
?>