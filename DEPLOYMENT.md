# Deployment Guide for Render

This guide will help you deploy your POS demo backend to Render's free tier.

## Prerequisites

1. A GitHub account
2. Your code pushed to a GitHub repository
3. A Render account (sign up at https://render.com - it's free)

## Step 1: Push Your Code to GitHub

If you haven't already:

```bash
# Initialize git (if not already done)
git init

# Add all files
git add .

# Commit
git commit -m "Initial commit - POS demo backend"

# Create a new repository on GitHub, then:
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
git branch -M main
git push -u origin main
```

## Step 2: Create PostgreSQL Database on Render

1. Go to https://dashboard.render.com
2. Click **"New +"** → **"PostgreSQL"**
3. Configure:
   - **Name**: `pos-db` (or any name you prefer)
   - **Database**: `posdb`
   - **User**: `posuser` (or leave default)
   - **Region**: Choose closest to you
   - **Plan**: **Free**
4. Click **"Create Database"**
5. Wait for database to be created (takes ~1-2 minutes)
6. Once ready, note the **Internal Database URL** (you'll need this)

## Step 3: Deploy Web Service

1. In Render dashboard, click **"New +"** → **"Web Service"**
2. Connect your GitHub repository:
   - Click **"Connect account"** if not connected
   - Authorize Render to access your repos
   - Select your repository
3. Configure the service:
   - **Name**: `pos-backend` (or any name)
   - **Region**: Same as database
   - **Branch**: `main` (or your default branch)
   - **Root Directory**: Leave empty (or `.` if needed)
   - **Environment**: **Java**
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/pos-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod`
   - **Plan**: **Free**

4. **Environment Variables** (click "Advanced"):
   Add these variables:
   ```
   SPRING_PROFILES_ACTIVE=prod
   DATABASE_URL=<Internal Database URL from Step 2>
   JAVA_OPTS=-Xmx512m -Xms256m
   ```
   
   **Important**: 
   - Use the **Internal Database URL** (not the External one) for better performance and security
   - The Internal URL looks like: `postgresql://user:password@dpg-xxxxx-a/posdb`
   - Copy it from your database's "Connections" tab in Render dashboard

5. Click **"Create Web Service"**

## Step 4: Wait for Deployment

- Render will:
  1. Clone your repo
  2. Run the build command
  3. Start your application
- This takes ~5-10 minutes the first time
- Watch the logs in the Render dashboard

## Step 5: Verify Deployment

Once deployed, you'll get a URL like: `https://pos-backend.onrender.com`

Test your API:
- Swagger UI: `https://pos-backend.onrender.com/swagger-ui.html`
- Health check: `https://pos-backend.onrender.com/terminals` (should return empty array `[]`)

## Step 6: Update Your Flutter App

Update your Flutter app's base URL:
```dart
const String BASE_URL = 'https://pos-backend.onrender.com';
```

## Troubleshooting

### Build Fails
- Check build logs in Render dashboard
- Ensure `mvnw` (Maven wrapper) is in your repo
- Verify Java version compatibility

### Database Connection Issues
- Verify `DATABASE_URL` environment variable is set correctly
- Check that database is in same region as web service
- Ensure you're using **Internal Database URL** (not External)

### App Crashes on Startup
- Check logs in Render dashboard
- Verify all environment variables are set
- Check that PostgreSQL driver is in dependencies

### Slow First Request
- Render free tier spins down after 15 minutes of inactivity
- First request after spin-down takes ~30 seconds to wake up
- This is normal for free tier

## Environment Variables Reference

| Variable | Value | Description |
|----------|-------|-------------|
| `SPRING_PROFILES_ACTIVE` | `prod` | Activates production profile |
| `DATABASE_URL` | (from Render) | PostgreSQL connection string |
| `JAVA_OPTS` | `-Xmx512m -Xms256m` | JVM memory settings |
| `PORT` | (auto-set by Render) | Server port (don't set manually) |

## Free Tier Limitations

- **750 hours/month** (enough for 24/7 if you're the only user)
- **Spins down after 15 min inactivity** (first request after wake-up is slow)
- **512MB RAM** (sufficient for demo)
- **Database**: 1GB storage (plenty for demo)

## Upgrading (Optional)

If you need:
- Always-on service (no spin-down)
- More resources
- Custom domain

You can upgrade to paid plans starting at $7/month.

## Support

- Render Docs: https://render.com/docs
- Render Community: https://community.render.com

