//const loginBtn = document.getElementById("login-btn");
//const signupBtn = document.getElementById("signup-btn");
const loginForm = document.getElementById("login-form");
const registerForm = document.getElementById("register-form");
const formModal = document.getElementById("form-modal");
//const showRegisterLink = document.getElementById("show-register");
const token = localStorage.getItem("jwtToken");
const logoutBtn = document.getElementById("logout-Btn");

// show login form
const loginBtn = document.getElementById("login-btn");
if (loginBtn) {
  loginBtn.addEventListener("click", () => {
    formModal.style.display = "flex";
    loginForm.style.display = "block";
    registerForm.style.display = "none";
  });
}

// show registration form
const signupBtn = document.getElementById("signup-btn");
if (signupBtn) {
  signupBtn.addEventListener("click", () => {
    formModal.style.display = "flex";
    registerForm.style.display = "block";
    loginForm.style.display = "none";
    // registerForm.style.display = "block";
  });
}
//  show registration form from login
const showRegisterLink = document.getElementById("show-register");
if (showRegisterLink) {
  showRegisterLink.addEventListener("click", (e) => {
    e.preventDefault();
    loginForm.style.display = "none";
    registerForm.style.display = "block";
  });
}

// close modal when clicking outside form

window.addEventListener("click", (e) => {
  if (e.target == formModal) {
    formModal.style.display = "none";
  }
});

registerForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const userData = {
    username: document.getElementById("username").value,
    email: document.getElementById("email").value,
    password: document.getElementById("password").value,
    mobile: document.getElementById("mobile").value,
  };
  console.log("Submitting:", userData);

  fetch("http://localhost:9090/api/v1/message/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(userData),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log("User Registered:", data);
      if (data.status === 201) {
        alert("Registration Successfull");
        registerForm.reset();
        document.getElementById("form-modal").style.display = "none";
      } else if (data.status === 409) {
        alert("username is already Available"); //
      } else if (data.status === 410) {
        alert("Email is already Available");
      } else if (data.status === 411) {
        alert("Mobile is already Registered");
      } else {
        alert("Registration failed");
      }
    })

    .catch((error) => console.error("Error", error));
});

loginForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const loginData = {
    email: document.getElementById("login-email").value.trim(),
    password: document.getElementById("login-password").value.trim(),
  };

  console.log("Sending login data:", loginData);

  fetch("http://localhost:9090/api/v1/message/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(loginData),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log("Response from backend:", data); // <-- check response
      if (data.status === 200) {
        localStorage.setItem("jwtToken", data.data);
        window.location.href = "welcome.html";
      } else if (data.status === 401) {
        alert("Invalid Credentials");
      } else {
        alert("Login Failed:" + data.data);
      }
    })
    .catch((error) => {
      console.error("Error sending login request:", error);
      alert("Something went wrong. Check console.");
    });
});

// ===================== PROTECTED FETCH (for welcome.html or dashboard) ====

// function fetchProtectedData() {
//   const token = localStorage.getItem("jwtToken");
//   if (!token) {
//     alert("no token found, please login first");
//     window.location.href = "login.html";
//     return;
//   }
//   fetch("http://localhost:9090/api/v1/message/login/", {
//     method: "GET",
//     headers: {
//       Authorization: " Bearer " + token,
//       "Content-Type": "application/json",
//     },
//   })
//     .then((response) => {
//       if (response.status === 401) {
//         alert("unauthorized please login again");
//         window.location.href = "login.html";
//         return;
//       }
//       return response.json();
//     })
//     .then((data) => {
//       console.log("protected data", data);
//       const userInfo = document.getElementById("user-info");
//       if (userInfo) {
//         userInfo.textContent = 'Welcome ${data.username || "User"}!';
//       }
//     })
//     .catch((error) => console.error("error fetching protected data", data));
// }

// //---------Admin dashboard----------------

// if (!token) {
//   alert("Please login first!");
//   window.location.href = "login.html";
// }

// fetch("http://localhost:9090/api/v1/admin/dashboard", {
//   method: "GET",
//   headers: {
//     Authorization: "Bearer " + token,
//   },
// })
//   .then((res) => {
//     if (res.status === 401) {
//       alert("Unauthorized! Please login again.");
//       window.location.href = "login.html";
//     } else if (res.status === 403) {
//       document.getElementById("admin-data").innerText =
//         "Access Denied! You are not an Admin.";
//     } else if (!res.ok) {
//       throw new Error("Request failed with status " + res.status);
//     } else {
//       return res.text();
//     }
//   })
//   .then((text) => {
//     if (text) {
//       document.getElementById("admin-data").innerText = text;
//     }
//   })
//   .catch((err) => {
//     console.error("Fetch error:", err);
//     document.getElementById("admin-data").innerText = "Error fetching data.";
//   });

// //----------Logout-----------
// // const logoutBtn = document.getElementById("logout-Btn");

// // logoutBtn.addEventListener("click", function () {
// //   // Remove token
// //   localStorage.removeItem("token");

// //   // Redirect to login page
// //   window.location.href = "index.html";
// // });
// document.addEventListener("DOMContentLoaded", () => {
//   if (logoutBtn) {
//     console.log("Logout button found:", logoutBtn);
//     logoutBtn.addEventListener("click", () => {
//       console.log("Logout clicked!");
//       localStorage.removeItem("jwtToken");
//       window.location.href = "index.html";
//     });
//   } else {
//     console.error("Logout button NOT found!");
//   }
// });
