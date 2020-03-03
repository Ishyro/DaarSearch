import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-document-view',
  templateUrl: './document-view.component.html',
  styleUrls: ['./document-view.component.css']
})
export class DocumentViewComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DocumentViewComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  nameBook = '';
  contentBook = '';

  ngOnInit() {
    this.nameBook = this.data.book.name;
    this.contentBook = this.data.book.content;
    //console.log("ohayo :" + this.data.book.name);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
