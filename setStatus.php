<?php 
    include_once "dbconnect.php";
    $idUser = (int) $_GET["idUser"];
    $idPost = $_GET["idPost"];
    $status = $_GET["status"];
        
    $sql = "SELECT * FROM votes WHERE idUser = $idUser AND idPost = $idPost";
    $result = $connect->query($sql);

    if(!$result)
        die($connect->error);
    else if($result->num_rows == 0){
        $sql = "INSERT INTO votes 
                VALUES(null, $idPost, $idUser, $status)";
        $result = $connect->query($sql);
    
        if(!$result)
            die($connect->error);

        echo "Insert Success";
    }
    else{
        $sql = "UPDATE votes
            SET status = $status
            WHERE idUser = $idUser
            AND idPost = $idPost";
    
        $result = $connect->query($sql);
    
        if(!$result)
            die($connect->error);

        echo "Update Success";
    }    
    $connect->close();
?>