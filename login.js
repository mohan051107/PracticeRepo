// Dummy user credentials for testing
const DUMMY_USERS = [
    { username: 'student', password: 'student123', role: 'student' },
    { username: 'teacher', password: 'teacher123', role: 'teacher' },
    { username: 'admin', password: 'admin123', role: 'admin' }
];

// Handle login form submission
function handleLogin(event) {
    event.preventDefault();
    
    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();
    const rememberMe = document.getElementById('rememberMe').checked;
    
    // Validate input
    if (!username || !password) {
        showMessage('Please enter both username and password', 'error');
        return;
    }
    
    // Check credentials against dummy users
    const user = DUMMY_USERS.find(u => 
        u.username.toLowerCase() === username.toLowerCase() && 
        u.password === password
    );
    
    if (user) {
        // Successful login
        showMessage(`Welcome ${user.username}! Redirecting to dashboard...`, 'success');
        
        // Store user info in session/local storage
        const storage = rememberMe ? localStorage : sessionStorage;
        storage.setItem('currentUser', JSON.stringify(user));
        storage.setItem('isLoggedIn', 'true');
        
        // Redirect to dashboard after 1.5 seconds
        setTimeout(() => {
            window.location.href = 'dashboard.html';
        }, 1500);
    } else {
        // Failed login
        showMessage('Invalid username or password. Please try again.', 'error');
        
        // Clear password field
        document.getElementById('password').value = '';
        document.getElementById('password').focus();
    }
}

// Show error/success messages
function showMessage(message, type) {
    // Remove existing messages
    const existingError = document.querySelector('.error-message');
    const existingSuccess = document.querySelector('.success-message');
    if (existingError) existingError.remove();
    if (existingSuccess) existingSuccess.remove();
    
    // Create message element
    const messageDiv = document.createElement('div');
    messageDiv.className = type === 'error' ? 'error-message show' : 'success-message show';
    messageDiv.textContent = message;
    
    // Insert message after the form
    const form = document.getElementById('loginForm');
    form.parentNode.insertBefore(messageDiv, form);
    
    // Auto-hide success messages after 5 seconds
    if (type === 'success') {
        setTimeout(() => {
            messageDiv.classList.remove('show');
            setTimeout(() => messageDiv.remove(), 300);
        }, 5000);
    }
}

// Add enter key support
document.addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        const form = document.getElementById('loginForm');
        if (document.activeElement === document.getElementById('username') || 
            document.activeElement === document.getElementById('password')) {
            form.dispatchEvent(new Event('submit'));
        }
    }
});

// Check if user is already logged in
window.addEventListener('load', function() {
    const storage = localStorage.getItem('isLoggedIn') === 'true' ? localStorage : 
                    sessionStorage.getItem('isLoggedIn') === 'true' ? sessionStorage : null;
    
    if (storage) {
        const user = JSON.parse(storage.getItem('currentUser'));
        if (user) {
            // Auto-redirect to dashboard if already logged in
            window.location.href = 'dashboard.html';
        }
    }
    
    // Add focus to username field
    document.getElementById('username').focus();
    
    // Add demo credentials click feature
    const credentialSpans = document.querySelectorAll('.dummy-credentials span');
    credentialSpans.forEach(span => {
        span.style.cursor = 'pointer';
        span.addEventListener('click', function() {
            const text = this.textContent;
            const parentText = this.parentElement.textContent;
            if (parentText.includes('Username')) {
                document.getElementById('username').value = text;
            } else if (parentText.includes('Password')) {
                document.getElementById('password').value = text;
            }
        });
    });
});

// Forgot password functionality
document.querySelector('.forgot-password')?.addEventListener('click', function(e) {
    e.preventDefault();
    showMessage('Please contact your administrator to reset your password.', 'error');
});

// Sign up link functionality
document.querySelector('.signup-link a')?.addEventListener('click', function(e) {
    e.preventDefault();
    showMessage('Registration feature coming soon!', 'error');
});

// Quick fill demo credentials
function fillCredentials(username, password) {
    document.getElementById('username').value = username;
    document.getElementById('password').value = password;
}