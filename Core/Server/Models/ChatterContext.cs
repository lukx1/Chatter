using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace Server.Models
{
    public partial class ChatterContext : DbContext
    {
        public ChatterContext()
        {
        }

        public ChatterContext(DbContextOptions<ChatterContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Cfiles> Cfiles { get; set; }
        public virtual DbSet<Messages> Messages { get; set; }
        public virtual DbSet<Relationships> Relationships { get; set; }
        public virtual DbSet<Rooms> Rooms { get; set; }
        public virtual DbSet<Roomusers> Roomusers { get; set; }
        public virtual DbSet<Users> Users { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseMySql(SCCFG.GetConnectionString());
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Cfiles>(entity =>
            {
                entity.HasKey(e => e.Uuid)
                    .HasName("PRIMARY");

                entity.ToTable("cfiles");

                entity.HasIndex(e => e.Idroom)
                    .HasName("FK_cfiles_rooms");

                entity.HasIndex(e => e.Iduploader)
                    .HasName("FK_cfiles_users");

                entity.Property(e => e.Uuid)
                    .HasColumnName("UUID")
                    .HasMaxLength(16);

                entity.Property(e => e.FileName)
                    .IsRequired()
                    .HasColumnType("varchar(64)");

                entity.Property(e => e.Idroom)
                    .HasColumnName("IDRoom")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Iduploader)
                    .HasColumnName("IDUploader")
                    .HasColumnType("int(11)");

                entity.HasOne(d => d.IdroomNavigation)
                    .WithMany(p => p.Cfiles)
                    .HasForeignKey(d => d.Idroom)
                    .HasConstraintName("FK_cfiles_rooms");

                entity.HasOne(d => d.IduploaderNavigation)
                    .WithMany(p => p.Cfiles)
                    .HasForeignKey(d => d.Iduploader)
                    .HasConstraintName("FK_cfiles_users");
            });

            modelBuilder.Entity<Messages>(entity =>
            {
                entity.ToTable("messages");

                entity.HasIndex(e => e.IdroomReceiver)
                    .HasName("FK_messages_rooms");

                entity.HasIndex(e => e.Idsender)
                    .HasName("FK_messages_users");

                entity.Property(e => e.Id)
                    .HasColumnName("ID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Content)
                    .IsRequired()
                    .HasColumnType("varchar(4096)")
                    .HasDefaultValueSql("''");

                entity.Property(e => e.DateEdited).HasColumnType("datetime");

                entity.Property(e => e.DateRead).HasColumnType("datetime");

                entity.Property(e => e.DateReceived).HasColumnType("datetime");

                entity.Property(e => e.DateSent)
                    .HasColumnType("datetime")
                    .HasDefaultValueSql("'CURRENT_TIMESTAMP'");

                entity.Property(e => e.Edited).HasColumnType("bit(1)");

                entity.Property(e => e.IdroomReceiver)
                    .HasColumnName("IDRoomReceiver")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Idsender)
                    .HasColumnName("IDSender")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Received).HasColumnType("bit(1)");

                entity.Property(e => e.Seen).HasColumnType("bit(1)");

                entity.Property(e => e.Writing).HasColumnType("bit(1)");

                entity.HasOne(d => d.IdroomReceiverNavigation)
                    .WithMany(p => p.Messages)
                    .HasForeignKey(d => d.IdroomReceiver)
                    .HasConstraintName("FK_messages_rooms");

                entity.HasOne(d => d.IdsenderNavigation)
                    .WithMany(p => p.Messages)
                    .HasForeignKey(d => d.Idsender)
                    .HasConstraintName("FK_messages_users");
            });

            modelBuilder.Entity<Relationships>(entity =>
            {
                entity.ToTable("relationships");

                entity.HasIndex(e => e.IdsourceUser)
                    .HasName("FK_relationships_users");

                entity.HasIndex(e => e.IdtargetUser)
                    .HasName("FK_relationships_users_2");

                entity.Property(e => e.Id)
                    .HasColumnName("ID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.DateCreated)
                    .HasColumnType("datetime")
                    .HasDefaultValueSql("'CURRENT_TIMESTAMP'");

                entity.Property(e => e.IdsourceUser)
                    .HasColumnName("IDSourceUser")
                    .HasColumnType("int(11)");

                entity.Property(e => e.IdtargetUser)
                    .HasColumnName("IDTargetUser")
                    .HasColumnType("int(11)");

                entity.Property(e => e.RelationType).HasColumnType("int(11)");

                entity.HasOne(d => d.IdsourceUserNavigation)
                    .WithMany(p => p.RelationshipsIdsourceUserNavigation)
                    .HasForeignKey(d => d.IdsourceUser)
                    .HasConstraintName("FK_relationships_users");

                entity.HasOne(d => d.IdtargetUserNavigation)
                    .WithMany(p => p.RelationshipsIdtargetUserNavigation)
                    .HasForeignKey(d => d.IdtargetUser)
                    .HasConstraintName("FK_relationships_users_2");
            });

            modelBuilder.Entity<Rooms>(entity =>
            {
                entity.ToTable("rooms");

                entity.Property(e => e.Id)
                    .HasColumnName("ID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Name).HasColumnType("varchar(64)");

                entity.Property(e => e.OneOnOne).HasColumnType("bit(1)");
            });

            modelBuilder.Entity<Roomusers>(entity =>
            {
                entity.ToTable("roomusers");

                entity.Property(e => e.Id)
                    .HasColumnName("ID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Idroom)
                    .HasColumnName("IDRoom")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Iduser)
                    .HasColumnName("IDUser")
                    .HasColumnType("int(11)");
            });

            modelBuilder.Entity<Users>(entity =>
            {
                entity.ToTable("users");

                entity.HasIndex(e => e.Login)
                    .HasName("Login")
                    .IsUnique();

                entity.Property(e => e.Id)
                    .HasColumnName("ID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.DateLastLogin)
                    .HasColumnType("datetime")
                    .HasDefaultValueSql("'CURRENT_TIMESTAMP'");

                entity.Property(e => e.DateRegistered)
                    .HasColumnType("datetime")
                    .HasDefaultValueSql("'CURRENT_TIMESTAMP'");

                entity.Property(e => e.Email).HasColumnType("varchar(256)");

                entity.Property(e => e.FirstName)
                    .IsRequired()
                    .HasColumnType("varchar(64)")
                    .HasDefaultValueSql("''");

                entity.Property(e => e.Login)
                    .IsRequired()
                    .HasColumnType("varchar(64)");

                entity.Property(e => e.Password).HasColumnType("char(68)");

                entity.Property(e => e.PictureUrl)
                    .HasColumnName("PictureURL")
                    .HasColumnType("varchar(256)");

                entity.Property(e => e.SecondName)
                    .IsRequired()
                    .HasColumnType("varchar(64)")
                    .HasDefaultValueSql("''");

                entity.Property(e => e.Status)
                    .HasColumnType("int(11)")
                    .HasDefaultValueSql("'0'");
            });
        }
    }
}
