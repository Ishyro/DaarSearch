import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { AuthService } from '../auth.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<LoginComponent>, public authService: AuthService) {}

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onLogin(form: NgForm) {
    if (form.invalid) {
      return;
    }
    this.authService.login(form.value.email, form.value.password);
    this.dialogRef.close();
  }
}
