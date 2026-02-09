# ParkEase Backend Deployment Guide

This guide walks you through deploying the ParkEase backend to Render.

## Prerequisites

1. **GitHub Repository**: Your code must be pushed to a GitHub repository
2. **Render Account**: Sign up at [render.com](https://render.com)
3. **Supabase Database**: Your PostgreSQL database should be running and accessible

## Deployment Steps

### 1. Prepare Your Repository

Ensure all deployment files are committed and pushed:
```bash
git add build.sh start.sh render.yaml DEPLOYMENT.md
git commit -m "Add Render deployment configuration"
git push origin main
```

### 2. Create a New Web Service on Render

1. Log in to [Render Dashboard](https://dashboard.render.com)
2. Click **"New +"** → **"Web Service"**
3. Connect your GitHub account (if not already connected)
4. Select your `parkease` repository
5. Configure the service:
   - **Name**: `parkease-backend`
   - **Region**: Choose closest to your users
   - **Branch**: `main` (or your default branch)
   - **Root Directory**: `parkease-backend`
   - **Runtime**: `Java`
   - **Build Command**: `chmod +x build.sh && ./build.sh`
   - **Start Command**: `chmod +x start.sh && ./start.sh`

### 3. Configure Environment Variables

In the Render dashboard, add these environment variables:

| Key | Value | Notes |
|-----|-------|-------|
| `SPRING_PROFILES_ACTIVE` | `prod` | Activates production profile |
| `DATABASE_URL` | `jdbc:postgresql://db.bxidlvpleddpwflfcqpw.supabase.co:5432/postgres` | Your Supabase connection string |
| `DATABASE_USERNAME` | `postgres` | Your database username |
| `DATABASE_PASSWORD` | `your-password` | Your Supabase password |
| `JWT_SECRET` | `your-secure-random-string-min-256-bits` | Generate a secure random string |
| `CORS_ORIGINS` | `https://yourfrontend.vercel.app` | Your frontend URL(s), comma-separated |
| `JAVA_TOOL_OPTIONS` | `-Xmx512m` | Java memory settings |

> **Security Tip**: Generate a strong JWT secret using:
> ```bash
> openssl rand -base64 64
> ```

### 4. Deploy

1. Click **"Create Web Service"**
2. Render will automatically:
   - Clone your repository
   - Run the build script
   - Start your application
3. Monitor the deployment logs for any errors

### 5. Verify Deployment

Once deployed, test your API:

1. **Health Check**:
   ```bash
   curl https://parkease-backend.onrender.com/api/auth/health
   ```

2. **Register a User**:
   ```bash
   curl -X POST https://parkease-backend.onrender.com/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{
       "email": "test@example.com",
       "password": "password123",
       "name": "Test User",
       "role": "USER"
     }'
   ```

3. **Login**:
   ```bash
   curl -X POST https://parkease-backend.onrender.com/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{
       "email": "test@example.com",
       "password": "password123"
     }'
   ```

### 6. Update Frontend

Update your frontend environment variables to point to the deployed backend:

```env
VITE_API_URL=https://parkease-backend.onrender.com
```

## Troubleshooting

### Build Fails

- **Check Java Version**: Ensure Render is using Java 17
- **Maven Wrapper**: Verify `mvnw` has execute permissions
- **Dependencies**: Check `pom.xml` for any missing dependencies

### Application Won't Start

- **Port Configuration**: Render sets `$PORT` automatically, ensure your start script uses it
- **Database Connection**: Verify database credentials and URL
- **Logs**: Check Render logs for specific error messages

### CORS Errors

- **Origins**: Ensure `CORS_ORIGINS` includes your frontend URL
- **Protocol**: Use `https://` for production URLs
- **Multiple Origins**: Separate with commas: `https://app1.com,https://app2.com`

### Database Connection Issues

- **Supabase IP**: Ensure Render's IP addresses are allowed in Supabase
- **Connection String**: Verify the JDBC URL format is correct
- **SSL**: Supabase requires SSL connections

## Monitoring

- **Logs**: View real-time logs in Render dashboard
- **Metrics**: Monitor CPU, memory, and response times
- **Alerts**: Set up email alerts for service failures

## Automatic Deployments

Render automatically deploys when you push to your main branch. To disable:
1. Go to service settings
2. Toggle **"Auto-Deploy"** off

## Custom Domain (Optional)

1. Go to service settings → **"Custom Domain"**
2. Add your domain (e.g., `api.parkease.com`)
3. Update DNS records as instructed
4. Update `CORS_ORIGINS` to include your custom domain

## Cost Optimization

- **Free Tier**: Render offers a free tier with limitations
- **Sleep**: Free services sleep after 15 minutes of inactivity
- **Upgrade**: Consider paid plans for production workloads

## Support

- **Render Docs**: [docs.render.com](https://docs.render.com)
- **Community**: [community.render.com](https://community.render.com)
- **Status**: [status.render.com](https://status.render.com)
