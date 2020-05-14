<?php
require_once('connection.php');
$nim = $_POST['nim'];
$nama = $_POST['nama'];
$jurusan = $_POST['jurusan'];
$email = $_POST['email'];
if(!$nim || !$nama || !$jurusan || !$email){
  echo json_encode(array('message'=>'required field is empty.'));
}else{
$query = mysqli_query($CON, "UPDATE mahasiswa SET nama='$nama', jurusan='$jurusan', email='$email' WHERE nim = '$nim'");
if($query){
    echo json_encode(array('message'=>'mahasiswa data successfully updated.'));
  }else{
    echo json_encode(array('message'=>'mahasiswa data failed to update.'));
  }
}
?>