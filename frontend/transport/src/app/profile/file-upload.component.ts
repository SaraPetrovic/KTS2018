import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

  selectedFile: File = null;

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  onFileSelected(event){
    this.selectedFile = <File>event.target.files[0];
  }

  onUpload(){
    const fd = new FormData();
    fd.append('file', this.selectedFile, this.selectedFile.name);
    this.http.post('http://localhost:9003/files/upload', fd).subscribe(
      res => { 
        console.log(res);
        alert("Successfully upload");
      }
    );
  }
}
