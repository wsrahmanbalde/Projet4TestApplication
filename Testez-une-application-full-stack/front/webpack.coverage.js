module.exports = {
  module: {
    rules: [
      {
        test: /\.[jt]sx?$/,
        exclude: /(node_modules|\.spec\.ts$)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: [
              '@babel/preset-typescript'
            ],
            plugins: [
              ['@babel/plugin-proposal-decorators', { legacy: true }],
              ['@babel/plugin-proposal-class-properties', { loose: true }],
              'istanbul'
            ]
          }
        }
      }
    ]
  }
};