<?php
    include_once "dbconnect.php";
    $title =$_GET['title_news'];
    $link =$_GET['link'];
    $idUser =$_GET['idUser'];

    $sql = "DELETE FROM news_save WHERE link = '$link' AND idUser = $idUser";
    $result = $connect->query($sql);
    if(!$result){
        die("Lỗi" + $connect->error);
    }
    echo "Success";
    $connect->close();

?>