using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Threading.Tasks;

using Microsoft.AspNetCore.Cryptography;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;


namespace Server.Helpers
{
    public class HashAndSalt
    {
        public byte[] PasswordHash { get; set; }
        public byte[] Salt { get; set; }

        public override string ToString()
        {
            return Convert.ToBase64String(AsBytes());
        }

        public static HashAndSalt FromString(string s)
        {
            var bytes = Convert.FromBase64String(s);
            return FromBytes(bytes);
        }

        public static HashAndSalt FromBytes(byte[] bytes)
        {
            var res = new HashAndSalt()
            {
                Salt = new byte[PasswordHelper.SaltLength],
                PasswordHash = new byte[PasswordHelper.HashLenght]
            };
            
            Array.Copy(bytes, res.PasswordHash, PasswordHelper.HashLenght);
            Array.Copy(bytes, PasswordHelper.HashLenght, res.Salt, 0, PasswordHelper.SaltLength);
            return res;
        }

        public byte[] AsBytes()
        {            
            return PasswordHash.Concat(Salt).ToArray();
        }
    }

    public class PasswordHelper
    {
        public const int SaltLength = 128 / 8;
        public const int HashLenght = 256 / 8;
        public const int IterCount = 10000;

        public static bool VerifyPasswordPbkdf2(string plain, string hashed)
        {
            return VerifyPasswordPbkdf2(plain, HashAndSalt.FromString(hashed));
        }

        public static bool VerifyPasswordPbkdf2(string plain, byte[] hashed)
        {
            return VerifyPasswordPbkdf2(plain, HashAndSalt.FromBytes(hashed));
        }

        public static bool VerifyPasswordPbkdf2(string plain, HashAndSalt hashed)
        {
            byte[] newHash = KeyDerivation.Pbkdf2(
                password: plain,
                salt: hashed.Salt,
                prf: KeyDerivationPrf.HMACSHA256,
                iterationCount: IterCount,
                numBytesRequested: HashLenght
                );

            return SlowEquals(hashed.PasswordHash, newHash);
        }

        public static HashAndSalt HashPasswordPbkdf2(string password)
        {
            var res = new HashAndSalt();
            var rng = RandomNumberGenerator.Create();

            res.Salt = new byte[SaltLength];
            rng.GetBytes(res.Salt);

            res.PasswordHash = KeyDerivation.Pbkdf2(
                password: password,
                salt: res.Salt,
                prf: KeyDerivationPrf.HMACSHA256,
                iterationCount: IterCount,
                numBytesRequested: HashLenght
                );

            return res;
        }

        public static bool SlowEquals(byte[] a, byte[] b)
        {
            var diff = (uint)a.Length ^ (uint)b.Length;
            for (int i = 0; i < a.Length && i < b.Length; i++)
            {
                diff |= (uint)(a[i] ^ b[i]);
            }
            return diff == 0;
        }

    }
}
