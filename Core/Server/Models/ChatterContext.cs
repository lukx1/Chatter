using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace Server.Models
{
    public partial class ChatterContext : DbContext
    {


        private static DbContextOptions GetOptions()
        {
            return MySqlDbContextOptionsExtensions.UseMySql(new DbContextOptionsBuilder(),  "Server=78.102.218.164;Port=7688;Database=chatter;User=conApp;Password=igR7DqL3HcvnHJ2q;CharSet=utf8;").Options;
        }

        public ChatterContext()
            : base(GetOptions())
        {
            
        }

        public virtual DbSet<Cfiles> Cfiles { get; set; }
        public virtual DbSet<Messages> Messages { get; set; }
        public virtual DbSet<Relationships> Relationships { get; set; }
        public virtual DbSet<Rooms> Rooms { get; set; }
        public virtual DbSet<Roomusers> Roomusers { get; set; }
        public virtual DbSet<Users> Users { get; set; }

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

                entity.HasIndex(e => e.Idcreator)
                    .HasName("FK_rooms_users");

                entity.HasIndex(e => e.Picture)
                    .HasName("FK_rooms_cfiles");

                entity.Property(e => e.Id)
                    .HasColumnName("ID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Idcreator)
                    .HasColumnName("IDCreator")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Name).HasColumnType("varchar(64)");

                entity.Property(e => e.OneOnOne).HasColumnType("bit(1)");

                entity.Property(e => e.Picture).HasMaxLength(16);

                entity.HasOne(d => d.IdcreatorNavigation)
                    .WithMany(p => p.Rooms)
                    .HasForeignKey(d => d.Idcreator)
                    .HasConstraintName("FK_rooms_users");
            });

            modelBuilder.Entity<Roomusers>(entity =>
            {
                entity.ToTable("roomusers");

                entity.HasIndex(e => e.Idroom)
                    .HasName("FK_roomusers_rooms");

                entity.HasIndex(e => e.Iduser)
                    .HasName("FK_roomusers_users");

                entity.Property(e => e.Id)
                    .HasColumnName("ID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Idroom)
                    .HasColumnName("IDRoom")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Iduser)
                    .HasColumnName("IDUser")
                    .HasColumnType("int(11)");

                entity.HasOne(d => d.IdroomNavigation)
                    .WithMany(p => p.Roomusers)
                    .HasForeignKey(d => d.Idroom)
                    .HasConstraintName("FK_roomusers_rooms");

                entity.HasOne(d => d.IduserNavigation)
                    .WithMany(p => p.Roomusers)
                    .HasForeignKey(d => d.Iduser)
                    .HasConstraintName("FK_roomusers_users");
            });

            modelBuilder.Entity<Users>(entity =>
            {
                entity.ToTable("users");

                entity.HasIndex(e => e.Login)
                    .HasName("Login")
                    .IsUnique();

                entity.HasIndex(e => e.Picture)
                    .HasName("FK_users_cfiles");

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

                entity.Property(e => e.Password).HasMaxLength(48);

                entity.Property(e => e.Picture).HasMaxLength(16);

                entity.Property(e => e.SecondName)
                    .IsRequired()
                    .HasColumnType("varchar(64)")
                    .HasDefaultValueSql("''");

                entity.Property(e => e.Status)
                    .HasColumnType("int(11)")
                    .HasDefaultValueSql("'0'");

                entity.HasOne(d => d.PictureNavigation)
                    .WithMany(p => p.Users)
                    .HasForeignKey(d => d.Picture)
                    .OnDelete(DeleteBehavior.Cascade)
                    .HasConstraintName("FK_users_cfiles");
            });
        }
    }
}
