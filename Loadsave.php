<?php
include_once 'dbConnect.php';   
$title =$_GET['title_news'];
$link =$_GET['link'];
$idUser =$_GET['idUser'];
$url_news = $_GET['url_news'];

$sql = "SELECT * FROM news_save WHERE idUser=$idUser ORDER BY id DESC";
$result = $connect->query($sql);
$data;
if(!$result){
    die($connect->error);
}
if($result->num_rows > 0){
    while($rows = $result->fetch_assoc()){
        $data[] = $rows;
    }
}
echo json_encode($data);

$connect->close();

?>