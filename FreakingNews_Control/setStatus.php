<?php 
    include_once "dbconnect.php";
    $idUser = $_GET["idUser"];
    $idPost = $_GET["idPost"];
    $status = $_GET["status"];
        
    $sql = "UPDATE votes
            SET status = $status
            WHERE idUser = $idUser
            AND idPost = $idPost";
    
    $result = $connect->query($sql);
    
    if(!$result)
        die($connect->error);

    echo "Success";
?>