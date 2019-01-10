using System;
using Server.Helpers;
using Xunit;

namespace Server.Tests
{
    public class PasswordHelperTest
    {
        [Fact]
        public void TestBasicHashAndVerify()
        {
            var hashed = PasswordHelper.HashPasswordPbkdf2("abcd");
            Assert.True(PasswordHelper.VerifyPasswordPbkdf2("abcd", hashed));
        }

        [Fact]
        public void TestVerifyFromString()
        {
            var hashed = PasswordHelper.HashPasswordPbkdf2("abcd");
            Assert.True(PasswordHelper.VerifyPasswordPbkdf2("abcd", hashed.ToString()));
        }
        
        [Fact]
        public void TestVerifyFromByteArray()
        {
            var hashed = PasswordHelper.HashPasswordPbkdf2("abcd");
            Assert.True(PasswordHelper.VerifyPasswordPbkdf2("abcd", hashed.AsBytes()));
        }
        
        [Fact]
        public void TestSlowEquals()
        {
            byte[] a = {1, 8, 9, 4, 5, 3, 7, 5};
            byte[] b = {1, 8, 9, 4, 5, 3, 7, 5};
            byte[] c = {1, 8, 5, 4, 33, 3, 7, 5};
            
            Assert.True(PasswordHelper.SlowEquals(a, b));
            Assert.False(PasswordHelper.SlowEquals(a, c));
        }
    }
}