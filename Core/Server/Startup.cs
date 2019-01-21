using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using Microsoft.Extensions.FileProviders;
using System.IO;
using System.Timers;
using Microsoft.EntityFrameworkCore;
using Server.Models;


namespace Server
{
    public class Startup
    {
        private Timer timer;
        private ChatterContext context;

        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        public void ConfigureServices(IServiceCollection services)
        {
            services.Configure<CookiePolicyOptions>(options => { 
                options.CheckConsentNeeded = context => false;
                options.MinimumSameSitePolicy = SameSiteMode.Lax;
            });

            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1);
            services.AddMemoryCache();
            services.AddSession();
        }

        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseHsts();
            }
            app.UseStaticFiles(new StaticFileOptions
            {
                FileProvider = new PhysicalFileProvider(
            Path.Combine(Directory.GetCurrentDirectory(), "Content")),
                RequestPath = "/Content"
            });
            app.UseCors(b =>
                b.WithOrigins("http://78.102.218.164:5000")
                .AllowAnyHeader()
            );
            app.UseHttpsRedirection();
            app.UseStaticFiles();
            app.UseCookiePolicy();
            app.UseSession();
            app.UseMvc(routes =>
            {
                routes.MapRoute(
                    name: "default",
                    template: "{controller=Home}/{action=Index}/{id?}");
            });
            timer = new Timer();
            timer.Interval = 600000;
            timer.Elapsed += TimerOnElapsed;
            timer.Start();
            TimerOnElapsed(null,null);
        }

        private void TimerOnElapsed(object sender, ElapsedEventArgs e)
        {
            if (context == null)
            {
                context = new ChatterContext();
            }

            var users = context.Users.Where(u => (DateTime.Now - u.DateLastLogin).Minutes > 10);
            foreach (var user in users)
            {
                user.Status = 0;
            }
            context.SaveChanges();
        }
    }
}