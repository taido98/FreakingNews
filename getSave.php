<?php
include_once 'dbConnect.php';    
// session_start();
// $id_User = (int) $_SESSION["id_User"];
// $id_User = 2;
$title =$_POST['title_news'];
$link =$_POST['link_news'];
$idUser =$_POST['idUser'];
$url_news = $_POST['url_news'];
//Cai nay co roi

$result = mysqli_query($connect,"SELECT `id` FROM `news_save` WHERE link = '$link' AND idUser = '$idUser'" );
/* Dong tren nay la sao 
Dung de set xem link ddc luu chua nhuwng thieu dk , chưa đủ?
 */
if($result->num_rows == 0){
    $query = "INSERT INTO news_save VALUES(null, $idUser, '$title', '$link', '$url_news')";
    if(mysqli_query($connect, $query)){

        // echo "success";
    }
    else{
        echo $idUser;
    }
}
else{
    echo "Tin đã lưu !";
}

$connect->close();
?>